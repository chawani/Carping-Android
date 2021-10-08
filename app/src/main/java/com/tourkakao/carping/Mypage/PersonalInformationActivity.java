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
                if(!checkAll()){
                    myToast = Toast.makeText(getApplicationContext(),"모두 입력해주세요", Toast.LENGTH_SHORT);
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
                // 입력되는 텍스트에 변화가 있을 때 호출된다.
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때 호출된다.
                if (binding.nickname.getText().toString().length() == 0) {
                    nickname = false;
                } else {
                    nickname = true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에 호출된다.
            }
        });
        binding.bio.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때 호출된다.
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때 호출된다.
                if (binding.bio.getText().toString().length() == 0) {
                    bio = false;
                } else {
                    bio = true;
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
                            myToast = Toast.makeText(getApplicationContext(),"수정 완료", Toast.LENGTH_SHORT);
                            myToast.show();
                            finish();
                        }
                        else {
                            myToast = Toast.makeText(getApplicationContext(),"수정 실패. 카핑 채널로 문의해주세요", Toast.LENGTH_SHORT);
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
            checked+=(binding.interest1.getText().toString()+",");
        }
        if(binding.interest2.isChecked()){
            checked+=(binding.interest2.getText().toString()+",");
        }
        if(binding.interest3.isChecked()){
            checked+=(binding.interest3.getText().toString()+",");
        }
        if(binding.interest4.isChecked()){
            checked+=(binding.interest4.getText().toString()+",");
        }
        if(binding.interest5.isChecked()){
            checked+=(binding.interest5.getText().toString()+",");
        }
        if(binding.interest6.isChecked()){
            checked+=(binding.interest6.getText().toString()+",");
        }
        if(binding.interest7.isChecked()){
            checked+=(binding.interest7.getText().toString()+",");
        }
        if(binding.interest8.isChecked()){
            checked+=(binding.interest8.getText().toString()+",");
        }
        if(binding.interest9.isChecked()){
            checked+=(binding.interest9.getText().toString()+",");
        }
        checked=checked.substring(0,checked.length()-1);
        return checked;
    }
}