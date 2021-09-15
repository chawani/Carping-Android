package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tourkakao.carping.databinding.ActivityPostRegister3Binding;
import com.tourkakao.carping.databinding.ActivityPostSettingBinding;

public class PostSettingActivity extends AppCompatActivity {
    private ActivityPostSettingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}