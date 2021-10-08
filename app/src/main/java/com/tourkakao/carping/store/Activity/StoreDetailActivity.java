package com.tourkakao.carping.store.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityStoreDetailBinding;

public class StoreDetailActivity extends AppCompatActivity {
    ActivityStoreDetailBinding detailBinding;
    Context context;
    String image, price, name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailBinding=ActivityStoreDetailBinding.inflate(getLayoutInflater());
        setContentView(detailBinding.getRoot());
        context=this;

        getting_intent();
        setting_data();
        setting_to_kakao_page();
        setting_back_btn();
    }
    public void getting_intent(){
        Intent intent=getIntent();
        image=intent.getStringExtra("image");
        price=intent.getStringExtra("price");
        name=intent.getStringExtra("name");
    }
    public void setting_data(){
        Glide.with(context).load(R.drawable.to_kakaopage_img).into(detailBinding.toKakaopageBtn);
        Glide.with(context).load(image).into(detailBinding.image);
        Glide.with(context).load(R.drawable.back).into(detailBinding.back);
        Glide.with(context).load(R.drawable.store_explain).into(detailBinding.explain);
        detailBinding.name.setText(name);
        detailBinding.price.setText(price);
    }
    public void setting_to_kakao_page(){
        detailBinding.toKakaopageBtn.setOnClickListener(v -> {
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://pf.kakao.com/_Cnxdvs"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
    public void setting_back_btn(){
        detailBinding.back.setOnClickListener(v -> {
            finish();
        });
    }
}