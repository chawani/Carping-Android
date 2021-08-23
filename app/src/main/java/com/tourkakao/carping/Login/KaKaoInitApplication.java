package com.tourkakao.carping.Login;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;
import com.tourkakao.carping.BuildConfig;

public class KaKaoInitApplication extends Application {
    private static KaKaoInitApplication instance;
    private final String LOGIN_APPKEY=BuildConfig.KAKAO_NATIVE_APPKEY;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        KakaoSdk.init(this, LOGIN_APPKEY);
    }
}
