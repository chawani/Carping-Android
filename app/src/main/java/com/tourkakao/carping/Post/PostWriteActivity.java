package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tourkakao.carping.databinding.ActivityPostRegisterBinding;
import com.tourkakao.carping.databinding.ActivityPostWriteBinding;

public class PostWriteActivity extends AppCompatActivity {
    private ActivityPostWriteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}