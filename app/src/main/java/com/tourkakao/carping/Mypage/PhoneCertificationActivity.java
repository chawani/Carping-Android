package com.tourkakao.carping.Mypage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.tourkakao.carping.databinding.ActivityMypageActivitiesBinding;
import com.tourkakao.carping.databinding.ActivityPhoneCertificationBinding;

public class PhoneCertificationActivity extends AppCompatActivity {
    ActivityPhoneCertificationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPhoneCertificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        settingToolbar();
    }

    void settingToolbar(){
        Toolbar toolbar=binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}