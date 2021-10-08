package com.tourkakao.carping.Mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityHelpListBinding;
import com.tourkakao.carping.databinding.ActivityProfileEditBinding;

public class HelpListActivity extends AppCompatActivity {
    private ActivityHelpListBinding binding;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityHelpListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();

        initLayout();

        binding.q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, HelpDetailActivity.class);
                intent.putExtra("q",1);
                startActivity(intent);
            }
        });
        binding.q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, HelpDetailActivity.class);
                intent.putExtra("q",2);
                startActivity(intent);
            }
        });
        binding.q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, HelpDetailActivity.class);
                intent.putExtra("q",3);
                startActivity(intent);
            }
        });
        binding.q4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, HelpDetailActivity.class);
                intent.putExtra("q",4);
                startActivity(intent);
            }
        });
        binding.q5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, HelpDetailActivity.class);
                intent.putExtra("q",5);
                startActivity(intent);
            }
        });
        binding.q6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, HelpDetailActivity.class);
                intent.putExtra("q",6);
                startActivity(intent);
            }
        });
        binding.q7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, HelpDetailActivity.class);
                intent.putExtra("q",7);
                startActivity(intent);
            }
        });
        binding.q8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, HelpDetailActivity.class);
                intent.putExtra("q",8);
                startActivity(intent);
            }
        });
        binding.q9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, HelpDetailActivity.class);
                intent.putExtra("q",9);
                startActivity(intent);
            }
        });
        binding.q10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, HelpDetailActivity.class);
                intent.putExtra("q",10);
                startActivity(intent);
            }
        });
        binding.q11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, HelpDetailActivity.class);
                intent.putExtra("q",11);
                startActivity(intent);
            }
        });
        binding.channel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://pf.kakao.com/_Cnxdvs"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    void initLayout(){
        Glide.with(context).load(R.drawable.kakao_channel_img).into(binding.channel);
        Glide.with(getApplicationContext()).load(R.drawable.back).into(binding.back);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}