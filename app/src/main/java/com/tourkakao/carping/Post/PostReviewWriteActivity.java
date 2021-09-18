package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tourkakao.carping.databinding.ActivityPostReviewWriteBinding;
import com.tourkakao.carping.databinding.ActivityPostWriteBinding;

public class PostReviewWriteActivity extends AppCompatActivity {
    private ActivityPostReviewWriteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostReviewWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}