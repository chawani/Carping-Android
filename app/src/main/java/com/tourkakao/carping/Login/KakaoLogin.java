package com.tourkakao.carping.Login;

import android.content.Context;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class KakaoLogin implements LoginContract.Kakaologin{
    Context context;
    Function2<OAuthToken, Throwable, Unit> kakaoCallback;

    public KakaoLogin(Context context) {
        this.context = context;
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

    public void setting_kakaocallback(){
        kakaoCallback=new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if(oAuthToken!=null){
                    oAuthToken.getAccessToken();
                }
                return null;
            }
        };
    }
}
