package com.tourkakao.carping.Home.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.HomeFragmentBinding;

public class HomeFragment extends Fragment {
    HomeFragmentBinding homebinding;
    EcoTopFragment eco_top_fragment;
    EcoFragment eco_fragment;
    ThemeTopFragment theme_top_fragment;
    ThemeFragment theme_fragment;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homebinding=HomeFragmentBinding.inflate(inflater, container, false);
        context=getActivity().getApplicationContext();

        init_main_fragment();
        switch_main_tap();

        return homebinding.getRoot();
    }
    public void init_main_fragment(){
        eco_top_fragment =new EcoTopFragment();
        eco_fragment = new EcoFragment();
        theme_top_fragment=new ThemeTopFragment();
        theme_fragment = new ThemeFragment();
    }
    public void switch_main_tap(){
        getChildFragmentManager().beginTransaction().add(R.id.top_container, theme_top_fragment).commit();
        getChildFragmentManager().beginTransaction().add(R.id.container, theme_fragment).commit();

        homebinding.tabs.addTab(homebinding.tabs.newTab().setText("테마카핑"));
        homebinding.tabs.addTab(homebinding.tabs.newTab().setText("에코카핑"));
        homebinding.tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment top_selected=null;
                Fragment selected = null;
                if(position == 0) {
                    top_selected = theme_top_fragment;
                    selected = theme_fragment;
                }
                else if(position == 1) {
                    top_selected = eco_top_fragment;
                    selected = eco_fragment;
                }
                getChildFragmentManager().beginTransaction().replace(R.id.top_container, top_selected).commit();
                getChildFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
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
