package com.tourkakao.carping.MypageMainActivities.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.tourkakao.carping.MypageMainActivities.Fragment.LikeCarpingFragment;
import com.tourkakao.carping.MypageMainActivities.Fragment.LikeEcoFragment;
import com.tourkakao.carping.MypageMainActivities.Fragment.MyCarpingFragment;
import com.tourkakao.carping.MypageMainActivities.Fragment.MyEcoFragment;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityEcoCarpingDetailBinding;
import com.tourkakao.carping.databinding.ActivityMypageActivitiesBinding;

public class MypageCarpingActivity extends AppCompatActivity {
    private ActivityMypageActivitiesBinding binding;
    private Fragment selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMypageActivitiesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        settingToolbar();
        settingTab();
    }

    void settingToolbar(){
        binding.toolbarTitle.setText("차박지");
        Toolbar toolbar=binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    void settingTab(){
        Fragment myFragment=new MyCarpingFragment();
        Fragment likeFragment=new LikeCarpingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mypage_container,myFragment).commit();
        binding.tabs.addTab(binding.tabs.newTab().setText("등록"));
        binding.tabs.addTab(binding.tabs.newTab().setText("스크랩"));

        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition() ;
                if (pos == 0) { // 첫 번째 탭 선택.
                    selected=myFragment;
                }
                if(pos==1){
                    selected=likeFragment;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.mypage_container,selected).commit();
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