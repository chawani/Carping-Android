package com.tourkakao.carping.Mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.tourkakao.carping.databinding.ActivityHelpDetailBinding;
import com.tourkakao.carping.databinding.ActivityHelpListBinding;

public class HelpDetailActivity extends AppCompatActivity {
    private ActivityHelpDetailBinding binding;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityHelpDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();

        int q=getIntent().getIntExtra("q",1);
        initLayout(q);
    }

    void initLayout(int q){
        if(q==1){
            return;
        }
        if(q==2){
            return;
        }
        if(q==3){
            return;
        }
        if(q==4){
            return;
        }
        if(q==5){
            return;
        }
        if(q==6){
            return;
        }
        if(q==7){
            return;
        }
        if(q==8){
            return;
        }
        if(q==9){
            return;
        }
        if(q==10){
            return;
        }
        if(q==11){
            return;
        }
    }
}