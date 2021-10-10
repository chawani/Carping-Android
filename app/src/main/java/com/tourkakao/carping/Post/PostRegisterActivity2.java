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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.Mypage.HelpListActivity;
import com.tourkakao.carping.Mypage.TermsOfServiceActivity;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.Post.DTO.ChannelInfo;
import com.tourkakao.carping.Post.DTO.PostInfoDetail;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityPostRegister2Binding;
import com.tourkakao.carping.databinding.ActivityPostRegisterBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.SchedulerSupport;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PostRegisterActivity2 extends AppCompatActivity {
    private ActivityPostRegister2Binding binding;
    private boolean check_channel=false;
    private boolean check_openchat=false;
    private Context context;
    private Gson gson=new Gson();
    private String channel_introduce;
    private String openchat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostRegister2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();

        settingInfo();
        observeChanges();

        Glide.with(context).load(R.drawable.back).into(binding.back);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.completionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!check_channel){
                    Toast myToast = Toast.makeText(getApplicationContext(),"채널 소개를 작성해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                if(!check_openchat){
                    Toast myToast = Toast.makeText(getApplicationContext(),"오픈 채팅방 주소를 작성해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                if(!checkAgreeList()){
                    Toast myToast = Toast.makeText(getApplicationContext(),"항목들에 모두 체크해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                Intent intent=new Intent(context, PostRegisterActivity3.class);
                intent.putExtra("channel",binding.channel.getText().toString());
                intent.putExtra("openchat",binding.openchat.getText().toString());
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
        binding.openchat.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때 호출된다.
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때 호출된다.
                if(binding.openchat.getText().toString().length()==0){
                    check_openchat=false;
                }
                else{
                    check_openchat=true;
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
                    //binding.check4.setChecked(true);
                    changeButtonColor(checkAll());
                }
                else{
                    binding.check1.setChecked(false);
                    binding.check2.setChecked(false);
                    binding.check3.setChecked(false);
                    //binding.check4.setChecked(false);
                    changeButtonColor(checkAll());
                }
            }
        });
        binding.terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, TermsOfServiceActivity.class);
                startActivity(intent);
            }
        });
    }

    void setData(List data){
        String total=gson.toJson(data.get(0));
        ChannelInfo item =gson.fromJson(total, ChannelInfo.class);
        if(item.getAuthor_comment()!=null) {
            channel_introduce = item.getAuthor_comment();
        }
        if(item.getKakao_openchat_url()!=null) {
            openchat = item.getKakao_openchat_url();
        }
        binding.channel.setText(channel_introduce);
        binding.openchat.setText(openchat);
    }

    void settingInfo(){
        TotalApiClient.getPostApiService(context).getChannelInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            setData(commonClass.getData());
                        }
                        else {
                            System.out.println("요청실패:"+commonClass.getCode()+commonClass.getError_message());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("에러"+e.getMessage());
                    }
                });
    }

    public boolean checkAgreeList(){
        if(binding.check1.isChecked()
                &&binding.check2.isChecked()
                &&binding.check3.isChecked()
                /*&&binding.check4.isChecked()*/)
            return true;
        return false;
    }

    public boolean checkAll(){
        if(check_channel&&check_openchat&&checkAgreeList())
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
//        binding.check4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                changeButtonColor(checkAll());
//            }
//        });
    }
}