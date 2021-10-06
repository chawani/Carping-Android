package com.tourkakao.carping.thisweekend.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.thisweekend.viewmodel.Each_ThisWeekend_ViewModel;
import com.tourkakao.carping.databinding.ActivityEachThisWeekendBinding;

public class Each_ThisWeekendActivity extends AppCompatActivity {
    private ActivityEachThisWeekendBinding eachThisWeekendBinding;
    Context context;
    Each_ThisWeekend_ViewModel eachThisWeekendViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eachThisWeekendBinding=ActivityEachThisWeekendBinding.inflate(getLayoutInflater());
        setContentView(eachThisWeekendBinding.getRoot());
        context=getApplicationContext();

        eachThisWeekendViewModel=new ViewModelProvider(this).get(Each_ThisWeekend_ViewModel.class);
        eachThisWeekendViewModel.setContext(context);
        eachThisWeekendViewModel.setPk(getIntent().getIntExtra("pk", 0));
        eachThisWeekendViewModel.get_each_thisweekend();

        //databinding 설정
        //액티비티 라이프사이클 참조하면서 데이터 업데이트되면 viewmodel 가져와서 변경
        eachThisWeekendBinding.setLifecycleOwner(this);
        eachThisWeekendBinding.setEachThisWeekendViewModel(eachThisWeekendViewModel);

        starting_observe_detail_count();
        starting_observe_images();
    }

    public void starting_observe_detail_count(){
        eachThisWeekendViewModel.count.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==3){
                    eachThisWeekendBinding.campsite3Layout.setVisibility(View.VISIBLE);
                    eachThisWeekendBinding.campsite2Layout.setVisibility(View.VISIBLE);
                    eachThisWeekendBinding.campsite1Layout.setVisibility(View.VISIBLE);
                }else if(integer==2){
                    eachThisWeekendBinding.campsite3Layout.setVisibility(View.GONE);
                    eachThisWeekendBinding.campsite2Layout.setVisibility(View.VISIBLE);
                    eachThisWeekendBinding.campsite1Layout.setVisibility(View.VISIBLE);
                }else if(integer==3){
                    eachThisWeekendBinding.campsite3Layout.setVisibility(View.GONE);
                    eachThisWeekendBinding.campsite2Layout.setVisibility(View.GONE);
                    eachThisWeekendBinding.campsite1Layout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void starting_observe_images(){
        eachThisWeekendViewModel.thumbnail.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Glide.with(context).load(s).into(eachThisWeekendBinding.eachThumbnail);
            }
        });
        eachThisWeekendViewModel.image1.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Glide.with(context).load(s).into(eachThisWeekendBinding.image1);
            }
        });
        eachThisWeekendViewModel.image2.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Glide.with(context).load(s).into(eachThisWeekendBinding.image2);
            }
        });
        eachThisWeekendViewModel.image3.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Glide.with(context).load(s).into(eachThisWeekendBinding.image3);
            }
        });
    }
}