package com.tourkakao.carping.Login;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KaKaoInitApplication extends Application {
    private static KaKaoInitApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;

        KakaoSdk.init(this, "KAKAO_API_KEY");
    }
}
