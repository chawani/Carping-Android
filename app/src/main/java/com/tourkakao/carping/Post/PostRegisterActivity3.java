package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tourkakao.carping.databinding.ActivityPostRegister2Binding;
import com.tourkakao.carping.databinding.ActivityPostRegister3Binding;

public class PostRegisterActivity3 extends AppCompatActivity {
    private ActivityPostRegister3Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostRegister3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}