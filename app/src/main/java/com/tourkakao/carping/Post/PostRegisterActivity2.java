package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tourkakao.carping.databinding.ActivityPostRegister2Binding;
import com.tourkakao.carping.databinding.ActivityPostRegisterBinding;

public class PostRegisterActivity2 extends AppCompatActivity {
    private ActivityPostRegister2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostRegister2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}