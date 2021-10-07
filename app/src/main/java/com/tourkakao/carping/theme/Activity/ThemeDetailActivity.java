package com.tourkakao.carping.theme.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.R;
import com.tourkakao.carping.newcarping.Fragment.InfoFragment;
import com.tourkakao.carping.theme.Fragment.ThemeInfoFragment;
import com.tourkakao.carping.theme.Fragment.ThemeRecommendFragment;
import com.tourkakao.carping.databinding.ActivityThemeDetailBinding;
import com.tourkakao.carping.theme.viewmodel.ThemeDetailViewModel;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class ThemeDetailActivity extends AppCompatActivity {
    private ActivityThemeDetailBinding detailBinding;
    public ThemeDetailViewModel detailViewModel;
    Context context;
    ThemeInfoFragment infoFragment;
    ThemeRecommendFragment recommendFragment;
    String name; int pk;
    boolean bookmark=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailBinding=ActivityThemeDetailBinding.inflate(getLayoutInflater());
        setContentView(detailBinding.getRoot());
        context=this;
        name=getIntent().getStringExtra("name");
        pk=getIntent().getIntExtra("pk", 0);

        detailViewModel=new ViewModelProvider(this).get(ThemeDetailViewModel.class);
        detailViewModel.setContext(context);
        detailBinding.setLifecycleOwner(this);
        detailBinding.setThemedetailviewmodel(detailViewModel);
        detailViewModel.name.setValue(name);

        infoFragment=new ThemeInfoFragment();
        infoFragment.setName(name);
        infoFragment.setPk(pk);
        infoFragment.searchblog();
        recommendFragment=new ThemeRecommendFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.theme_detail_frame, infoFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.theme_detail_frame, recommendFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(recommendFragment).commit();
        setting_tablayout();
        starting_observe_image();
        starting_observe_lat_and_lon();
        starting_observe_bookmark();
        setting_bookmark();
        setting_back_button();
    }

    public void setting_tablayout(){
        detailBinding.themeDetailTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos=tab.getPosition();
                if(pos==0){
                    getSupportFragmentManager().beginTransaction().show(infoFragment).commit();
                    getSupportFragmentManager().beginTransaction().hide(recommendFragment).commit();
                }else{
                    getSupportFragmentManager().beginTransaction().hide(infoFragment).commit();
                    getSupportFragmentManager().beginTransaction().show(recommendFragment).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    public void starting_observe_image(){
        detailViewModel.image.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!=null) {
                    Glide.with(context).load(s).into(detailBinding.themeMainImg);
                }else{
                    Glide.with(context).load(R.drawable.theme_no_img).into(detailBinding.themeMainImg);
                }
            }
        });
    }
    public void starting_observe_lat_and_lon(){
        detailViewModel.carping_lon.observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                recommendFragment.setLat(String.valueOf(detailViewModel.carping_lat.getValue()));
                recommendFragment.setLon(String.valueOf(aFloat));
                recommendFragment.search_tour();
            }
        });
    }
    public void starting_observe_bookmark(){
        detailViewModel.is_bookmark.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    bookmark=true;
                    Glide.with(context).load(R.drawable.mybookmark_img).into(detailBinding.bookmark);
                }else{
                    bookmark=false;
                    Glide.with(context).load(R.drawable.bookmark_img).into(detailBinding.bookmark);
                }
            }
        });
    }
    public void setting_bookmark(){
        detailBinding.bookmark.setOnClickListener(v -> {
            if(bookmark){
                TotalApiClient.getApiService(context).release_theme_bookmark(pk).subscribe();
                Glide.with(context).load(R.drawable.bookmark_img).into(detailBinding.bookmark);
                bookmark=false;
            }else{
                TotalApiClient.getApiService(context).set_theme_bookmark(pk).subscribe();
                Glide.with(context).load(R.drawable.mybookmark_img).into(detailBinding.bookmark);
                bookmark=true;
            }
        });
    }
    public void setting_back_button(){

        detailBinding.back.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        infoFragment.infoFragmentBinding.mapView.removeView(infoFragment.mapView);
        infoFragment.mapView=null;
    }
}