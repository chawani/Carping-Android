package com.tourkakao.carping.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.kakao.sdk.common.KakaoSdk;
import com.tourkakao.carping.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    ImageView kakao_btn, google_btn;
    KakaoLogin kakaoLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init_view();

        setting_kakao_login();
        setting_google_login();
    }

    public void init_view(){
        kakao_btn=findViewById(R.id.kakao_btn);
        google_btn=findViewById(R.id.google_btn);
    }

    public void setting_kakao_login(){
        kakao_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //KakoLogin class로
                kakaoLogin=new KakaoLogin(LoginActivity.this);
                kakaoLogin.Login();
            }
        });
    }

    public void setting_google_login(){
        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GoogleLogin class로
            }
        });
    }

    /*private void getHashKey(){
        PackageInfo packageInfo=null;
        try{
            packageInfo=getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        }catch(PackageManager.NameNotFoundException e){}
        for(Signature signature:packageInfo.signatures){
            try{
                MessageDigest md=MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }catch(NoSuchAlgorithmException e){}
        }
    }*/

}