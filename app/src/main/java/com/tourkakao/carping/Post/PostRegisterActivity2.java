package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.tourkakao.carping.databinding.ActivityPostRegister2Binding;
import com.tourkakao.carping.databinding.ActivityPostRegisterBinding;

public class PostRegisterActivity2 extends AppCompatActivity {
    private ActivityPostRegister2Binding binding;
    private boolean check_channel=false;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostRegister2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();

        observeChanges();

        binding.completionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!check_channel){
                    Toast myToast = Toast.makeText(getApplicationContext(),"채널 소개를 작성해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                if(!checkAgreeList()){
                    Toast myToast = Toast.makeText(getApplicationContext(),"항목들에 모두 체크해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                Intent intent=new Intent(context, PostRegisterActivity3.class);
                startActivity(intent);
                finish();
            }
        });
        binding.channel.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때 호출된다.
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때 호출된다.
                if(binding.channel.getText().toString().length()==0){
                    check_channel=false;
                }
                else{
                    check_channel=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에 호출된다.
            }
        });
        binding.checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    binding.check1.setChecked(true);
                    binding.check2.setChecked(true);
                    binding.check3.setChecked(true);
                    binding.check4.setChecked(true);
                    changeButtonColor(checkAll());
                }
                else{
                    binding.check1.setChecked(false);
                    binding.check2.setChecked(false);
                    binding.check3.setChecked(false);
                    binding.check4.setChecked(false);
                    changeButtonColor(checkAll());
                }
            }
        });
    }

    public boolean checkAgreeList(){
        if(binding.check1.isChecked()
                &&binding.check2.isChecked()
                &&binding.check3.isChecked()
                &&binding.check4.isChecked())
            return true;
        return false;
    }

    public boolean checkAll(){
        if(check_channel&&checkAgreeList())
            return true;
        return false;
    }

    public void changeButtonColor(boolean check){
        if(check)
            binding.completionButton.setBackgroundColor(Color.BLACK);
        else
            binding.completionButton.setBackgroundColor(Color.parseColor("#999999"));
    }

    public void observeChanges(){
        binding.check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeButtonColor(checkAll());
            }
        });
        binding.check2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeButtonColor(checkAll());
            }
        });
        binding.check3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeButtonColor(checkAll());
            }
        });
        binding.check4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeButtonColor(checkAll());
            }
        });
    }
}