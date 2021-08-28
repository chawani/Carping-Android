package com.tourkakao.carping.EcoCarping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityEcoCarpingDetailBinding;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EcoCarpingDetailActivity extends AppCompatActivity {
    ActivityEcoCarpingDetailBinding binding;
    int pk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEcoCarpingDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent=getIntent();
        pk=(int)Float.parseFloat(intent.getStringExtra("pk"));

        loadDetail();
        //ViewPager viewPager=binding.imageViewPager;
        //DetailPagerAdapter adapter=new DetailPagerAdapter(getApplicationContext());
        //viewPager.setAdapter(adapter);
    }

    public void loadDetail(){
        TotalApiClient.getEcoApiService(getApplicationContext()).getEcoCarpingDetail(pk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        setData(commonClass.getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void setData(List data){
        String total=new Gson().toJson(data.get(0));

    }
}