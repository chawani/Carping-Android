package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.databinding.ActivityPostRegisterBinding;

import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PostRegisterActivity extends AppCompatActivity {
    private ActivityPostRegisterBinding binding;
    private Context context;
    private boolean phone_check=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();

        initLayout();

        observeChanges();

        binding.numberPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber=binding.phoneNumber.getText().toString();
                if(phoneNumber.equals("")||!phoneNumber.matches("[+-]?\\d*(\\.\\d+)?")) {
                    Toast myToast = Toast.makeText(getApplicationContext(),"숫자만 입력하세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                HashMap<String,Object> phone=new HashMap<>();
                phone.put("phone",binding.phoneNumber.getText().toString());
                TotalApiClient.getMypageApiService(context).postPhoneNumber(phone)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CommonClass commonClass) {
                                if(commonClass.getCode()==200) {
                                    Gson gson=new Gson();
                                    String dataString=gson.toJson(commonClass.getData().get(0));
                                    System.out.println(dataString);
                                    Toast myToast = Toast.makeText(getApplicationContext(),"요청 완료", Toast.LENGTH_SHORT);
                                    myToast.show();
                                    binding.phoneCertificationArea.setVisibility(View.VISIBLE);
                                }
                                else {
                                    System.out.println("요청실패:"+commonClass.getCode()+commonClass.getError_message());
                                }
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                            }
                        });
            }
        });
        binding.certificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String authNumber=binding.certificationNumber.getText().toString();
                if(authNumber.equals("")||!authNumber.matches("[+-]?\\d*(\\.\\d+)?")) {
                    Toast myToast = Toast.makeText(getApplicationContext(),"숫자만 입력하세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                HashMap<String,Object> auth_num=new HashMap<>();
                auth_num.put("auth_num",binding.certificationNumber.getText().toString());
                TotalApiClient.getMypageApiService(context).postverificationNumber(auth_num)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CommonClass commonClass) {
                                if(commonClass.getCode()==200) {
                                    Gson gson=new Gson();
                                    String dataString=gson.toJson(commonClass.getData().get(0));
                                    JsonParser parser = new JsonParser();
                                    JsonElement element = parser.parse(dataString);
                                    String message = element.getAsJsonObject().get("message").getAsString();
                                    if(message.equals("인증 실패")){
                                        Toast myToast = Toast.makeText(getApplicationContext(),"인증 번호가 다릅니다", Toast.LENGTH_SHORT);
                                        myToast.show();
                                    }
                                    if(message.equals("인증 완료")){
                                        Toast myToast = Toast.makeText(getApplicationContext(),"인증 완료", Toast.LENGTH_SHORT);
                                        myToast.show();
                                        binding.certificationButton.setClickable(false);
                                        binding.numberPostButton.setClickable(false);
                                        phone_check=true;
                                        changeButtonColor(checkAll());
                                    }
                                    if(message.equals("인증 문자 발송을 다시 요청해주세요.")){
                                        Toast myToast = Toast.makeText(getApplicationContext(),"인증 문자 발송을 다시 요청해주세요.", Toast.LENGTH_SHORT);
                                        myToast.show();
                                    }
                                }
                                else {
                                    System.out.println("요청실패:"+commonClass.getCode()+commonClass.getError_message());
                                }
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                            }
                        });
            }
        });
        binding.completionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(!phone_check){
//                    Toast myToast = Toast.makeText(getApplicationContext(),"휴대폰 인증은 필수입니다", Toast.LENGTH_SHORT);
//                    myToast.show();
//                    return;
//                }
                if(!checkAgreeList()){
                    Toast myToast = Toast.makeText(getApplicationContext(),"필수 항목들에 모두 체크해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                Intent intent=new Intent(context, PostRegisterActivity2.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void initLayout(){
        binding.phoneCertificationArea.setVisibility(View.GONE);
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
        if(phone_check&&checkAgreeList())
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