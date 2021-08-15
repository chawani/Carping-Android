package com.tourkakao.carping.Theme.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityThemeBinding;

public class ThemeActivity extends AppCompatActivity {
    private ActivityThemeBinding themeBinding;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeBinding=ActivityThemeBinding.inflate(getLayoutInflater());
        setContentView(themeBinding.getRoot());
        context=getApplicationContext();

        Glide.with(context).load(R.drawable.back).into(themeBinding.back);
        setting_tab();
    }

    public void setting_tab(){
        themeBinding.themeTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position=tab.getPosition();
                switch(position){

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