package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityPostSearchBinding;
import com.tourkakao.carping.databinding.ActivityPostTotalBinding;

public class PostTotalActivity extends AppCompatActivity {
    private ActivityPostTotalBinding binding;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostTotalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();

        initLayout();

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PostSearchActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initLayout(){
        Glide.with(context).load(R.drawable.main_reading_glasses).into(binding.searchButton);
        Glide.with(context).load(R.drawable.right_arrow_black).into(binding.allOfTotal);
        Glide.with(context).load(R.drawable.right_arrow_black).into(binding.beginnerTotal);
        Glide.with(context).load(R.drawable.right_arrow_black).into(binding.carTotal);
    }
}