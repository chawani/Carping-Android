package com.tourkakao.carping.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tourkakao.carping.R;

public class LoginActivity extends AppCompatActivity {
    ImageView kakao_btn, google_btn;
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
}