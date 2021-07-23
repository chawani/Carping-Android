package com.tourkakao.carping.Login;

import android.content.Context;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.tourkakao.carping.Network.ApiClient;
import com.tourkakao.carping.Network.ApiInterface;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KakaoLogin implements LoginContract.Kakaologin{
    Context context;
    Function2<OAuthToken, Throwable, Unit> kakaoCallback;
    ApiInterface apiInterface;

    public KakaoLogin(Context context) {
        this.context = context;
        apiInterface=ApiClient.getApiService();
        setting_kakaocallback();
    }

    @Override
    public void Login(){
        if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(context)){
            UserApiClient.getInstance().loginWithKakaoTalk(context, kakaoCallback);
        }else{
            UserApiClient.getInstance().loginWithKakaoAccount(context, kakaoCallback);
        }
    }

    @Override
    public void setting_kakaocallback(){
        kakaoCallback=new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if(oAuthToken!=null){
                    Call<Kakao_User_Info> kakao_login=apiInterface.kakao_signin(oAuthToken.getAccessToken());
                    kakao_login.enqueue(new Callback<Kakao_User_Info>() {
                        @Override
                        public void onResponse(Call<Kakao_User_Info> call, Response<Kakao_User_Info> response) {
                            if(response.isSuccessful()){

                            }else{
                                System.out.println("user matching fail");
                            }
                        }
                        @Override
                        public void onFailure(Call<Kakao_User_Info> call, Throwable t) {
                            System.out.println(t);
                        }
                    });
                }
                return null;
            }
        };
    }
}
