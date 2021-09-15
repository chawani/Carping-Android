package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tourkakao.carping.databinding.ActivityMypageActivitiesBinding;
import com.tourkakao.carping.databinding.ActivityPostRegisterBinding;

public class PostRegisterActivity extends AppCompatActivity {
    private ActivityPostRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


}