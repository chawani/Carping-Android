package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tourkakao.carping.databinding.ActivityPostCategoryBinding;
import com.tourkakao.carping.databinding.ActivityPostDetailBinding;

public class PostCategoryActivity extends AppCompatActivity {
    private ActivityPostCategoryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}