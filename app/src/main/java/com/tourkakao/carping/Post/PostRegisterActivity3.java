package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityPostRegister2Binding;
import com.tourkakao.carping.databinding.ActivityPostRegister3Binding;

public class PostRegisterActivity3 extends AppCompatActivity {
    private ActivityPostRegister3Binding binding;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostRegister3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();

        initLayout();

        binding.completionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PostWriteActivity.class);
                String channel=getIntent().getStringExtra("channel");
                String openchat=getIntent().getStringExtra("openchat");
                intent.putExtra("channel",channel);
                intent.putExtra("openchat",openchat);
                startActivity(intent);
                finish();
            }
        });
    }

    public void initLayout(){
        Glide.with(context).load(R.drawable.cancel_img).into(binding.cancel);
        Glide.with(context).load(R.drawable.right_circle_img).into(binding.rightArrow);
        Glide.with(context).load(R.drawable.post_start_text).into(binding.text);
        Glide.with(context).load(R.drawable.post_start_img).into(binding.image);
    }
}