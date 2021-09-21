package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.tourkakao.carping.MypageMainActivities.Fragment.LikeCarpingFragment;
import com.tourkakao.carping.MypageMainActivities.Fragment.MyCarpingFragment;
import com.tourkakao.carping.Post.Fragment.IntroduceFragment;
import com.tourkakao.carping.Post.Fragment.QuestionFragment;
import com.tourkakao.carping.Post.Fragment.RecommendationFragment;
import com.tourkakao.carping.Post.Fragment.ReviewFragment;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityPostCategoryBinding;
import com.tourkakao.carping.databinding.ActivityPremiumPostBinding;

public class PremiumPostActivity extends AppCompatActivity {
    private ActivityPremiumPostBinding binding;
    private Fragment selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPremiumPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        settingToolbar();
        settingTab();
    }

    void settingToolbar(){
        Toolbar toolbar=binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    void settingTab(){
        Fragment introduceFragment=new IntroduceFragment();
        Fragment questionFragment=new QuestionFragment();
        Fragment recommendationFragment=new RecommendationFragment();
        Fragment reviewFragment=new ReviewFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.post_container,introduceFragment).commit();
        binding.tabs.addTab(binding.tabs.newTab().setText("소개"));
        binding.tabs.addTab(binding.tabs.newTab().setText("리뷰"));
        binding.tabs.addTab(binding.tabs.newTab().setText("문의"));
        binding.tabs.addTab(binding.tabs.newTab().setText("추천"));

        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition() ;
                if (pos == 0) { // 첫 번째 탭 선택.
                    selected=introduceFragment;
                }
                if(pos==1){
                    selected=reviewFragment;
                }
                if(pos==2){
                    selected=questionFragment;
                }
                if(pos==3){
                    selected=recommendationFragment;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.post_container,selected).commit();
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