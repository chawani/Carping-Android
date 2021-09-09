package com.tourkakao.carping.Mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tourkakao.carping.MypageMainActivities.Activity.MypageEcoReviewActivity;
import com.tourkakao.carping.databinding.ActivityEcoCarpingDetailBinding;
import com.tourkakao.carping.databinding.ActivityProfileEditBinding;

public class ProfileEditActivity extends AppCompatActivity {
    ActivityProfileEditBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityProfileEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.personalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), PersonalInformationActivity.class);
                startActivity(intent);
            }
        });
    }
}