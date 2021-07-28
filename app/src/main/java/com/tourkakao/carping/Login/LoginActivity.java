package com.tourkakao.carping.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.kakao.sdk.common.KakaoSdk;
import com.tourkakao.carping.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    ImageView kakao_btn, google_btn;
    KakaoLogin kakaoLogin;

    GoogleLogin googleLogin;
    private static final int RC_SIGN_IN = 9001;

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
                googleLogin=new GoogleLogin(LoginActivity.this);
                googleLogin.signIn();
            }
        });
    }

    @Override //구글로그인
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            googleLogin.handleSignInResult(task);
        }
    }
}