package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tourkakao.carping.databinding.ActivityPostRegisterBinding;
import com.tourkakao.carping.databinding.ActivityPostReviewBinding;

public class PostReviewActivity extends AppCompatActivity {
    private ActivityPostReviewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}