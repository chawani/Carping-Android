package com.tourkakao.carping.Login;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.tourkakao.carping.BuildConfig;
import com.tourkakao.carping.Home.MainActivity;
import com.tourkakao.carping.Network.ApiClient;
import com.tourkakao.carping.Network.ApiInterface;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleLogin implements LoginContract.GoogleLogin{
    private Context context;
    private LoginActivity loginActivity;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "GoogleActivity";
    private final String CLIENT_ID;
    private final String CLIENT_SECRET;
    private String authCode="";//인증코드
    private ApiInterface retrofitAPI;
    private ApiInterface retrofitAPI2;

    public  GoogleLogin(Context context, LoginActivity loginActivity){
        CLIENT_ID = BuildConfig.GOOGLE_CLIENT_ID;
        CLIENT_SECRET=BuildConfig.GOOGLE_CLIENT_SECRET;
        // 앱에 필요한 사용자 데이터를 요청하도록 로그인 옵션을 설정한다.
        // DEFAULT_SIGN_IN parameter는 유저의 ID와 기본적인 프로필 정보를 요청하는데 사용된다.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(CLIENT_ID)
                //.requestIdToken(CLIENT_ID)
                .requestEmail()
                .build();
        // 위에서 만든 GoogleSignInOptions을 사용해 GoogleSignInClient 객체를 만듦
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        this.context = context;
        this.loginActivity=loginActivity;
    }

    @Override
    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        ((LoginActivity)context).startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
            if (acct != null) {
                authCode = acct.getServerAuthCode();

                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl("https://oauth2.googleapis.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                retrofitAPI=retrofit.create(ApiInterface.class);

                HashMap<String,String>input=new HashMap<>();
                input.put("code",authCode);
                input.put("client_id",CLIENT_ID);
                input.put("client_secret",CLIENT_SECRET);
                input.put("grant_type","authorization_code");

                Call<Google_Access_Token> call=retrofitAPI.postAuth(input);
                call.enqueue(new Callback<Google_Access_Token>() {
                    @Override
                    public void onResponse(Call<Google_Access_Token> call, Response<Google_Access_Token> response) {
                        if(response.isSuccessful()){
                            Google_Access_Token data=response.body();
                            setting_googlelogin_callback(data.getAccess_token());
                        }else{
                            System.out.println("응답 코드:"+response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<Google_Access_Token> call, Throwable t) {
                        Log.getStackTraceString(t);
                    }
                });
            }
        } catch (ApiException e) {
            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    public void setting_googlelogin_callback(String access_token){
        retrofitAPI2=ApiClient.getApiService();
        Call<Google_Token_and_User_Info> google_login=retrofitAPI2.google_signin(access_token);
        google_login.enqueue(new Callback<Google_Token_and_User_Info>() {
            @Override
            public void onResponse(Call<Google_Token_and_User_Info> call, Response<Google_Token_and_User_Info> response) {
                if(response.body()==null){
                    System.out.println("값 없음");
                }
                else if(response.isSuccessful()){
                    Google_Token_and_User_Info token=response.body();
                    loginActivity.finish();
                    context.startActivity(new Intent(context, MainActivity.class));
                    System.out.println("성공:"+token.toString());
                }else{
                    System.out.println("응답 코드:"+response.code());
                }
            }
            @Override
            public void onFailure(Call<Google_Token_and_User_Info> call, Throwable t) {
                Log.getStackTraceString(t);
            }
        });
    }
}
