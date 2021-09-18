package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tourkakao.carping.databinding.ActivityPostDetailBinding;
import com.tourkakao.carping.databinding.ActivityPostRegisterBinding;

public class PostDetailActivity extends AppCompatActivity {
    private ActivityPostDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}