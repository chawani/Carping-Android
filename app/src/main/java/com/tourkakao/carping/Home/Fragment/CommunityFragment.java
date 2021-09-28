package com.tourkakao.carping.Home.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.CommunityFragmentBinding;

public class CommunityFragment extends Fragment {
    private CommunityFragmentBinding communitybinding;
    Context context;
    ShareFragment shareFragment;
    StoreFragment storeFragment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        communitybinding=CommunityFragmentBinding.inflate(inflater, container, false);
        context=getActivity().getApplicationContext();

        shareFragment=new ShareFragment();
        storeFragment=new StoreFragment();
        getChildFragmentManager().beginTransaction().add(R.id.community_frame, shareFragment).commit();
        getChildFragmentManager().beginTransaction().add(R.id.community_frame, storeFragment).commit();
        getChildFragmentManager().beginTransaction().hide(storeFragment).commit();

        setting_tablayout();

        return communitybinding.getRoot();
    }

    public void setting_tablayout(){
        communitybinding.communityTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos=tab.getPosition();
                if(pos==0){
                    getChildFragmentManager().beginTransaction().show(shareFragment).commit();
                    getChildFragmentManager().beginTransaction().hide(storeFragment).commit();
                }else{
                    getChildFragmentManager().beginTransaction().hide(shareFragment).commit();
                    getChildFragmentManager().beginTransaction().show(storeFragment).commit();
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
