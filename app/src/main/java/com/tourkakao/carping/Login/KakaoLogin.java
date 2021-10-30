package com.tourkakao.carping.Login;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.tourkakao.carping.Home.MainActivity;
import com.tourkakao.carping.LoginNetwork.ApiClient;
import com.tourkakao.carping.LoginNetwork.ApiInterface;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KakaoLogin implements LoginContract.Kakaologin{
    Context context;
    Function2<OAuthToken, Throwable, Unit> kakaologin_Callback;
    Function2<OAuthToken, Throwable, Unit> kakaologout_Callback;
    ApiInterface apiInterface;
    LoginActivity loginActivity;

    public KakaoLogin(Context context, LoginActivity loginActivity) {
        this.context = context;
        this.loginActivity=loginActivity;
        apiInterface=ApiClient.getApiService();
        setting_kakaologin_callback();
    }

    @Override
    public void Login(){
        if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(context)){
            UserApiClient.getInstance().loginWithKakaoTalk(context, kakaologin_Callback);
        }else{
            UserApiClient.getInstance().loginWithKakaoAccount(context, kakaologin_Callback);
        }
    }

    @Override
    public void setting_kakaologin_callback(){
        kakaologin_Callback=new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if(oAuthToken!=null){
                    Call<Kakao_Token_and_User_Info> kakao_login=apiInterface.kakao_signin(oAuthToken.getAccessToken());
                    kakao_login.enqueue(new Callback<Kakao_Token_and_User_Info>() {
                        @Override
                        public void onResponse(Call<Kakao_Token_and_User_Info> call, Response<Kakao_Token_and_User_Info> response) {
                            if(response.code()==403){
                                Toast myToast = Toast.makeText(loginActivity,"동일한 이메일의 계정이 이미 존재합니다. 다른 계정을 이용해주세요", Toast.LENGTH_LONG);
                                myToast.show();
                                KakaoLogout kakaoLogout=new KakaoLogout(context, loginActivity);
                                kakaoLogout.signOut();
                                return;
                            }else if(response.code()==400){
                                Toast.makeText(context, "유저 생성 중 에러가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                KakaoLogout kakaoLogout=new KakaoLogout(context, loginActivity);
                                kakaoLogout.signOut();
                                return;
                            }
                            else if(response.isSuccessful()){
                                //서버 액세스 토큰, 리프레쉬 토큰은 받았으니
                                //그걸 사용하고 카카오 api 서버는 로그아웃 되도록 하는 방법 고민 필요
                                SharedPreferenceManager.getInstance(context).setString("access_token", response.body().access_token);
                                SharedPreferenceManager.getInstance(context).setString("refresh_token", response.body().refresh_token);
                                SharedPreferenceManager.getInstance(context).setInt("id", response.body().getUser().getPk());
                                SharedPreferenceManager.getInstance(context).setString("profile", response.body().getUser().getProfile().getImage());
                                SharedPreferenceManager.getInstance(context).setString("email", response.body().getUser().getEmail());
                                SharedPreferenceManager.getInstance(context).setString("username", response.body().getUser().getUserkname());
                                SharedPreferenceManager.getInstance(context).setString("login", "kakao");
                                loginActivity.finish();
                                context.startActivity(new Intent(context, MainActivity.class));
                            }else{
                                Log.e("login error", response.message());
                                Toast.makeText(context, "로그인에 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(context, "login error"+response.message(), Toast.LENGTH_SHORT).show();
                                KakaoLogout kakaoLogout=new KakaoLogout(context, loginActivity);
                                kakaoLogout.signOut();
                            }
                        }
                        @Override
                        public void onFailure(Call<Kakao_Token_and_User_Info> call, Throwable t) {
                            Log.getStackTraceString(t);
                            Toast.makeText(context, "서버 통신이 불안정합니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(context, "server error"+t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(context, "다시 시도해주세요", Toast.LENGTH_SHORT).show();
                }
                return null;
            }
        };
    }
}
