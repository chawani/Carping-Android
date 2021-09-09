package com.tourkakao.carping.Mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tourkakao.carping.databinding.ActivityPersonalInformationBinding;
import com.tourkakao.carping.databinding.ActivityProfileEditBinding;

public class PersonalInformationActivity extends AppCompatActivity {
    ActivityPersonalInformationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPersonalInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}