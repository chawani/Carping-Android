package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.tourkakao.carping.MypageMainActivities.Fragment.LikeCarpingFragment;
import com.tourkakao.carping.MypageMainActivities.Fragment.MyCarpingFragment;
import com.tourkakao.carping.Post.DTO.PostInfoDetail;
import com.tourkakao.carping.Post.Fragment.IntroduceFragment;
import com.tourkakao.carping.Post.Fragment.QuestionFragment;
import com.tourkakao.carping.Post.Fragment.RecommendationFragment;
import com.tourkakao.carping.Post.Fragment.ReviewFragment;
import com.tourkakao.carping.Post.ViewModel.PostDetailViewModel;
import com.tourkakao.carping.Post.ViewModel.PostListViewModel;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityPostCategoryBinding;
import com.tourkakao.carping.databinding.ActivityPremiumPostBinding;

public class PremiumPostActivity extends AppCompatActivity {
    private ActivityPremiumPostBinding binding;
    private Fragment selected;
    private PostDetailViewModel viewModel;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPremiumPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();

        viewModel =new ViewModelProvider(this).get(PostDetailViewModel.class);
        viewModel.setContext(this);

        Intent intent=getIntent();
        int pk=(int)Double.parseDouble(intent.getStringExtra("pk"));
        viewModel.loadInfoDetail(pk);

        settingToolbar();
        settingTab();
        settingInfo();
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

    public void settingInfo(){
        viewModel.getPostInfo().observe(this, new Observer<PostInfoDetail>() {
            @Override
            public void onChanged(PostInfoDetail postInfoDetail) {
                //프로필, 작성자
                Glide.with(context).load(postInfoDetail.getThumbnail()).into(binding.thumbnail);
                if(postInfoDetail.getPoint()!=0){
                    Glide.with(context).load(R.drawable.premium_mark).into(binding.premiumImage);
                }else{
                    Glide.with(context).load(R.drawable.free_mark).into(binding.premiumImage);
                }
                binding.star.setText("★ "+postInfoDetail.getTotal_star_avg());
                binding.title.setText(postInfoDetail.getTitle());
                binding.point.setText(postInfoDetail.getPoint());
                if(postInfoDetail.isIs_liked()==false){
                    Glide.with(context).load(R.drawable.like_mark).into(binding.likeMark);
                }else{
                    Glide.with(context).load(R.drawable.is_pushed_like).into(binding.likeMark);
                }
                //binding.likeCount.setText(postInfoDetail.);
            }
        });

    }
}