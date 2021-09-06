package com.tourkakao.carping.theme.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
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
    private ThemeDetailViewModel detailViewModel;
    Context context;
    ThemeInfoFragment infoFragment;
    ThemeRecommendFragment recommendFragment;
    String name; int pk;
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
        infoFragment.setting_viewmodel(detailViewModel);
        infoFragment.searchblog();
        recommendFragment=new ThemeRecommendFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.theme_detail_frame, infoFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.theme_detail_frame, recommendFragment).commit();
        setting_tablayout();
    }

    public void setting_tablayout(){
        detailBinding.themeDetailTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos=tab.getPosition();
                if(pos==0){
                    getSupportFragmentManager().beginTransaction().show(infoFragment);
                    getSupportFragmentManager().beginTransaction().hide(recommendFragment);
                }else{
                    getSupportFragmentManager().beginTransaction().hide(infoFragment);
                    getSupportFragmentManager().beginTransaction().show(recommendFragment);
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
}