package com.tourkakao.carping.Mypage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Home.HomeViewModel.MypageViewModel;
import com.tourkakao.carping.Mypage.DTO.Profile;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.ActivityPersonalInformationBinding;

import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PersonalInformationActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    private ActivityPersonalInformationBinding binding;
    private Context context;
    private MypageViewModel myViewModel;
    private boolean nickname,bio,keyword=false;
    private int count=0;
    private Toast myToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPersonalInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();
        myViewModel =new ViewModelProvider(this).get(MypageViewModel.class);
        myViewModel.setContext(context);

        setInfo();
        writeCheck();

        String email=SharedPreferenceManager.getInstance(getApplicationContext()).getString("email","");
        binding.email.setText(email);
        binding.completionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.nickname.getText().toString().contains("????????????")){
                    myToast = Toast.makeText(getApplicationContext(),"???????????? \'????????????\'?????? ????????? ?????? ??? ??? ????????????", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                if(!checkAll()){
                    myToast = Toast.makeText(getApplicationContext(),"?????? ??????????????????", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                editComplete();
            }
        });
        binding.interest1.setOnCheckedChangeListener(this::onCheckedChanged);
        binding.interest2.setOnCheckedChangeListener(this::onCheckedChanged);
        binding.interest3.setOnCheckedChangeListener(this::onCheckedChanged);
        binding.interest4.setOnCheckedChangeListener(this::onCheckedChanged);
        binding.interest5.setOnCheckedChangeListener(this::onCheckedChanged);
        binding.interest6.setOnCheckedChangeListener(this::onCheckedChanged);
        binding.interest7.setOnCheckedChangeListener(this::onCheckedChanged);
        binding.interest8.setOnCheckedChangeListener(this::onCheckedChanged);
        binding.interest9.setOnCheckedChangeListener(this::onCheckedChanged);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            count++;
        }
        else {
            count--;
        }
        if(count==0)
            keyword=false;
        else
            keyword=true;
        changeButtonColor(checkAll());
    }

    public void writeCheck() {
        binding.nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ???????????? ???????????? ????????? ?????? ??? ????????????.
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // ????????? ????????? ??? ????????????.
                if (binding.nickname.getText().toString().length() == 0) {
                    nickname = false;
                } else {
                    nickname = true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ???????????? ?????? ????????????.
            }
        });
        binding.bio.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ???????????? ???????????? ????????? ?????? ??? ????????????.
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // ????????? ????????? ??? ????????????.
                if (binding.bio.getText().toString().length() == 0) {
                    bio = false;
                } else {
                    bio = true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ???????????? ?????? ????????????.
            }
        });
    }

    public boolean checkAll(){
        if(nickname&&bio&&keyword)
            return true;
        return false;
    }

    public void changeButtonColor(boolean check){
        if(check)
            binding.completionButton.setBackgroundColor(Color.BLACK);
        else
            binding.completionButton.setBackgroundColor(Color.parseColor("#999999"));
    }

    public void setInfo(){
        String loginType=SharedPreferenceManager.getInstance(context).getString("login","");
        if(loginType.equals("kakao")) {
            Glide.with(getApplicationContext()).load(R.drawable.kakao_small_icon).into(binding.kakaoIcon);
        }
        Glide.with(getApplicationContext()).load(R.drawable.back).into(binding.back);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        myViewModel.loadProfile();
        myViewModel.getProfileMutableLiveData().observe(this, new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                binding.nickname.setText(profile.getUsername());
                binding.bio.setText(profile.getBio());
            }
        });
    }

    public void editComplete(){
        HashMap<String, RequestBody> map=new HashMap<>();
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"),binding.nickname.getText().toString());
        RequestBody bio=RequestBody.create(MediaType.parse("text/plain"),binding.bio.getText().toString());
        RequestBody interest = RequestBody.create(MediaType.parse("text/plain"),sendCheck());
        map.put("username", username);
        map.put("bio", bio);
        map.put("interest",interest);
        int userId= SharedPreferenceManager.getInstance(context).getInt("id",0);
        TotalApiClient.getMypageApiService(context).postUserInfo(Integer.toString(userId),map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            myToast = Toast.makeText(getApplicationContext(),"?????? ??????", Toast.LENGTH_SHORT);
                            myToast.show();
                            finish();
                        }
                        else {
                            System.out.println("????????? ??????"+commonClass.getCode()+commonClass.getError_message());
                            myToast = Toast.makeText(getApplicationContext(),"?????? ??????. ?????? ????????? ??????????????????", Toast.LENGTH_SHORT);
                            myToast.show();
                            finish();
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

    public String sendCheck(){
        String checked="";
        if(binding.interest1.isChecked()){
            checked+=("0,");
        }
        if(binding.interest2.isChecked()){
            checked+=("1,");
        }
        if(binding.interest3.isChecked()){
            checked+=("2,");
        }
        if(binding.interest4.isChecked()){
            checked+=("3,");
        }
        if(binding.interest5.isChecked()){
            checked+=("4,");
        }
        if(binding.interest6.isChecked()){
            checked+=("5,");
        }
        if(binding.interest7.isChecked()){
            checked+=("6,");
        }
        if(binding.interest8.isChecked()){
            checked+=("7,");
        }
        if(binding.interest9.isChecked()){
            checked+=("8,");
        }
        checked = checked.substring(0, checked.length()-1);
        return checked;
    }
}