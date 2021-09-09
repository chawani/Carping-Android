package com.tourkakao.carping.Mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.tourkakao.carping.MypageMainActivities.Fragment.LikeCarpingFragment;
import com.tourkakao.carping.MypageMainActivities.Fragment.MyCarpingFragment;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityMypageActivitiesBinding;
import com.tourkakao.carping.databinding.ActivityProfileEditBinding;

public class PostApprovalActivity extends AppCompatActivity {
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
        binding.toolbarTitle.setText("포스트 현황");
        Toolbar toolbar=binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    void settingTab(){
        Fragment underReviewPostFragment=new UnderReviewPostFragment();
        Fragment reviewCompleteFragment=new ReviewCompleteFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mypage_container,underReviewPostFragment).commit();
        binding.tabs.addTab(binding.tabs.newTab().setText("심사중"));
        binding.tabs.addTab(binding.tabs.newTab().setText("완료"));

        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition() ;
                if (pos == 0) { // 첫 번째 탭 선택.
                    selected=underReviewPostFragment;
                }
                if(pos==1){
                    selected=reviewCompleteFragment;
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
