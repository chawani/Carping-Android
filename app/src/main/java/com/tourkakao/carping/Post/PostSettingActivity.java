package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityPostRegister3Binding;
import com.tourkakao.carping.databinding.ActivityPostSettingBinding;

public class PostSettingActivity extends AppCompatActivity {
    private ActivityPostSettingBinding binding;
    private Context context;
    private boolean radioCheck=false;
    private boolean priceCheck=false;
    private boolean introCheck=false;
    private boolean recommendCheck=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();

        binding.price.setClickable(false);
        binding.price.setFocusable(false);
        settingPaidPost();
        checkWriteAll();

        binding.completionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PostDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void settingPaidPost(){
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioCheck=true;
                if(i==R.id.pay){
                    if(binding.price.getText().toString().length()==0){
                        priceCheck=false;
                    }
                    else{
                        priceCheck=true;
                    }
                    binding.price.setFocusableInTouchMode (true);
                    binding.price.setFocusable(true);
                    binding.price.setBackground(ContextCompat.getDrawable(context, R.drawable.edit_text_box_purple));
                    changeButtonColor(checkAll());
                }
                if(i==R.id.free){
                    priceCheck=true;
                    binding.price.setClickable(false);
                    binding.price.setFocusable(false);
                    binding.price.setBackground(ContextCompat.getDrawable(context, R.drawable.edit_text_box_img));
                    changeButtonColor(checkAll());
                }
            }
        });
    }

    public void checkWriteAll(){
        binding.price.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때 호출된다.
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때 호출된다.
                if(binding.price.getText().toString().length()==0){
                    priceCheck=false;
                }
                else{
                    priceCheck=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에 호출된다.
            }
        });
        binding.introduce.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때 호출된다.
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때 호출된다.
                if(binding.introduce.getText().toString().length()==0){
                    introCheck=false;
                }
                else{
                    introCheck=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에 호출된다.
            }
        });
        binding.recommend.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때 호출된다.
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때 호출된다.
                if(binding.recommend.getText().toString().length()==0){
                    recommendCheck=false;
                }
                else{
                    recommendCheck=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에 호출된다.
            }
        });
    }

    public boolean checkAll(){
        if(radioCheck&&priceCheck&&introCheck&&recommendCheck)
            return true;
        return false;
    }

    public void changeButtonColor(boolean check){
        if(check)
            binding.completionButton.setBackgroundColor(Color.BLACK);
        else
            binding.completionButton.setBackgroundColor(Color.parseColor("#999999"));
    }
}