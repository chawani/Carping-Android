package com.tourkakao.carping.Mypage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.databinding.ActivityMypageActivitiesBinding;
import com.tourkakao.carping.databinding.ActivityPhoneCertificationBinding;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PhoneCertificationActivity extends AppCompatActivity {
    private ActivityPhoneCertificationBinding binding;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPhoneCertificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();

        settingToolbar();

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
                                        finish();
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
    }

    void settingToolbar(){
        Toolbar toolbar=binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}