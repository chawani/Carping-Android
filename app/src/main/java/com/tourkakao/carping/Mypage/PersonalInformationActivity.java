package com.tourkakao.carping.Mypage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.tourkakao.carping.Home.HomeViewModel.MypageViewModel;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.ActivityPersonalInformationBinding;
import com.tourkakao.carping.databinding.ActivityProfileEditBinding;

import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PersonalInformationActivity extends AppCompatActivity {
    private ActivityPersonalInformationBinding binding;
    private Context context;
    private MypageViewModel myViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPersonalInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();
        myViewModel =new ViewModelProvider(this).get(MypageViewModel.class);
        myViewModel.setContext(context);

        setInfo();

        String email=SharedPreferenceManager.getInstance(getApplicationContext()).getString("email","");
        binding.email.setText(email);
        binding.completionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editComplete();
            }
        });
    }

    public void setInfo(){
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
        String userId= SharedPreferenceManager.getInstance(context).getString("id","");
        TotalApiClient.getMypageApiService(context).postUserInfo(userId,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            Toast myToast = Toast.makeText(getApplicationContext(),"수정 완료", Toast.LENGTH_SHORT);
                            myToast.show();
                            finish();
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