package com.tourkakao.carping.MypageMainActivities.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.tourkakao.carping.MypageMainActivities.Fragment.LikeEcoFragment;
import com.tourkakao.carping.MypageMainActivities.Fragment.LikeSharingFragment;
import com.tourkakao.carping.MypageMainActivities.Fragment.MyEcoFragment;
import com.tourkakao.carping.MypageMainActivities.Fragment.MySharingFragment;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityMypageActivitiesBinding;

public class MypageFreeSharingActivity extends AppCompatActivity {
    private ActivityMypageActivitiesBinding binding;
    private Fragment selected=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMypageActivitiesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        settingToolbar();
        settingTab();
    }

    void settingToolbar(){
        binding.toolbarTitle.setText("무료나눔");
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Glide.with(getApplicationContext()).load(R.drawable.back).into(binding.back);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void settingTab(){
        Fragment myFragment=new MySharingFragment();
        Fragment likeFragment=new LikeSharingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mypage_container,myFragment).commit();
        binding.tabs.addTab(binding.tabs.newTab().setText("마이"));
        binding.tabs.addTab(binding.tabs.newTab().setText("좋아요"));

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