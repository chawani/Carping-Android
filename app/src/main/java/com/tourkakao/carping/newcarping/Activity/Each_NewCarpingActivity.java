package com.tourkakao.carping.newcarping.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityEachNewCarpingBinding;
import com.tourkakao.carping.newcarping.Fragment.InfoFragment;
import com.tourkakao.carping.newcarping.Fragment.ReviewFragment;
import com.tourkakao.carping.newcarping.viewmodel.EachNewCarpingViewModel;

public class Each_NewCarpingActivity extends AppCompatActivity {
    private ActivityEachNewCarpingBinding eachNewCarpingBinding;
    Context context;
    InfoFragment infoFragment;
    ReviewFragment reviewFragment;
    EachNewCarpingViewModel eachNewCarpingViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eachNewCarpingBinding=ActivityEachNewCarpingBinding.inflate(getLayoutInflater());
        setContentView(eachNewCarpingBinding.getRoot());
        context=getApplicationContext();

        eachNewCarpingViewModel=new ViewModelProvider(this).get(EachNewCarpingViewModel.class);
        eachNewCarpingViewModel.setContext(context);
        eachNewCarpingViewModel.setPk(getIntent().getIntExtra("pk", 0));
        eachNewCarpingBinding.setLifecycleOwner(this);
        eachNewCarpingBinding.setEachNewCarpingViewModel(eachNewCarpingViewModel);

        infoFragment=new InfoFragment();
        infoFragment.setting_viewmodel(eachNewCarpingViewModel);
        reviewFragment=new ReviewFragment();
        reviewFragment.setting_viewmodel(eachNewCarpingViewModel);
        getSupportFragmentManager().beginTransaction().add(R.id.newcarping_frame, infoFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.newcarping_frame, reviewFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(reviewFragment).commit();

        eachNewCarpingViewModel.get_newcarping_detail();

        setting_tablayout();
    }

    public void setting_tablayout(){
        eachNewCarpingBinding.newcarpingTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos=tab.getPosition();
                if(pos==0){
                    getSupportFragmentManager().beginTransaction().show(infoFragment).commit();
                    getSupportFragmentManager().beginTransaction().hide(reviewFragment).commit();
                }else{
                    getSupportFragmentManager().beginTransaction().hide(infoFragment).commit();
                    getSupportFragmentManager().beginTransaction().show(reviewFragment).commit();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

}