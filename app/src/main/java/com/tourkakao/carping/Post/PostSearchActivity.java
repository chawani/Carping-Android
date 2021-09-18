package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityPostDetailBinding;
import com.tourkakao.carping.databinding.ActivityPostSearchBinding;

public class PostSearchActivity extends AppCompatActivity {
    private ActivityPostSearchBinding binding;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();

        initLayout();
    }

    public void initLayout(){
        Glide.with(context).load(R.drawable.back).into(binding.back);
        Glide.with(context).load(R.drawable.main_reading_glasses).into(binding.searchImg);
    }
}