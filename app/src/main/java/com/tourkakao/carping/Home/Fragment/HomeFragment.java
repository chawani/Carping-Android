package com.tourkakao.carping.Home.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.tourkakao.carping.Home.SearchAdapter.SearchAdapter;
import com.tourkakao.carping.Home.SearchViewModel.SearchViewModel;
import com.tourkakao.carping.Home.ShareDataClass.Share;
import com.tourkakao.carping.MainSearch.Activity.MainSearchWith;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.HomeFragmentBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    public HomeFragmentBinding homebinding;
    EcoTopFragment eco_top_fragment;
    EcoFragment eco_fragment;
    ThemeTopFragment theme_top_fragment;
    ThemeFragment theme_fragment;
    Context context;
    int can_see_popular=1;
    public int is_slideup=0;
    int city_select=0;
    String region="서울";
    ArrayList<Integer> cities;
    ArrayList<Integer> s_cities;
    ArrayList<ImageView> imageViews;
    SearchViewModel searchViewModel;
    SearchAdapter searchAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homebinding=HomeFragmentBinding.inflate(inflater, container, false);
        context=getActivity().getApplicationContext();

        searchViewModel=new ViewModelProvider(this).get(SearchViewModel.class);
        searchViewModel.setContext(context);

        init_main_fragment();
        switch_main_tap();
        setting_images();
        //setting_sliding();
        setting_searchadapter();
        starting_observe_change_popular();
        setting_search_text();
        searchViewModel.get_popularlist(region);

        return homebinding.getRoot();
    }
    public void init_main_fragment(){
        eco_top_fragment =new EcoTopFragment();
        eco_fragment = new EcoFragment();
        theme_top_fragment=new ThemeTopFragment();
        theme_fragment = new ThemeFragment();
        theme_fragment.setRegion(region);
    }
    public void switch_main_tap(){
        getChildFragmentManager().beginTransaction().add(R.id.top_container, theme_top_fragment).commit();
        getChildFragmentManager().beginTransaction().add(R.id.top_container, eco_top_fragment).commit();
        getChildFragmentManager().beginTransaction().add(R.id.container, theme_fragment).commit();
        getChildFragmentManager().beginTransaction().hide(eco_top_fragment).commit();

        System.out.println("switch_main");
        homebinding.tabs.addTab(homebinding.tabs.newTab().setText("테마카핑"));
        homebinding.tabs.addTab(homebinding.tabs.newTab().setText("에코카핑"));
        homebinding.tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                //Fragment top_selected=null;
                Fragment selected = null;
                if(position == 0) {
                    SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setInt("newcarping", 1);
                    SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setInt("thisweekend", 1);
                    getChildFragmentManager().beginTransaction().hide(eco_top_fragment).commit();
                    getChildFragmentManager().beginTransaction().show(theme_top_fragment).commit();
                    //top_selected = theme_top_fragment;
                    selected = theme_fragment;
                    can_see_popular=1;
                }
                else if(position == 1) {
                    getChildFragmentManager().beginTransaction().hide(theme_top_fragment).commit();
                    getChildFragmentManager().beginTransaction().show(eco_top_fragment).commit();
                    //top_selected = eco_top_fragment;
                    selected = eco_fragment;
                    can_see_popular=0;
                }
                //getChildFragmentManager().beginTransaction().show(top_selected).commit();
                getChildFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
                if(can_see_popular==1){
                    searchViewModel.get_popularlist(region);
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
    public void setting_images(){
        cities=new ArrayList<>();
        cities.add(R.drawable.seoul); cities.add(R.drawable.incheon); cities.add(R.drawable.daejun); cities.add(R.drawable.gwangjoo);
        cities.add(R.drawable.daegu); cities.add(R.drawable.ulsan); cities.add(R.drawable.pusan); cities.add(R.drawable.gyeonggi);
        cities.add(R.drawable.kangwon); cities.add(R.drawable.chungnam); cities.add(R.drawable.chungbuk); cities.add(R.drawable.kyungnam);
        cities.add(R.drawable.kyungbuk); cities.add(R.drawable.junnam); cities.add(R.drawable.junbuk); cities.add(R.drawable.jeju);
        s_cities=new ArrayList<>();
        s_cities.add(R.drawable.seouls); s_cities.add(R.drawable.incheons); s_cities.add(R.drawable.daejuns); s_cities.add(R.drawable.kwangjus);
        s_cities.add(R.drawable.daegoos); s_cities.add(R.drawable.ulsans); s_cities.add(R.drawable.pusans); s_cities.add(R.drawable.kyeonggis);
        s_cities.add(R.drawable.kangwons); s_cities.add(R.drawable.chungnams); s_cities.add(R.drawable.chungbuks); s_cities.add(R.drawable.kyungnams);
        s_cities.add(R.drawable.kyungbuks); s_cities.add(R.drawable.junnams); s_cities.add(R.drawable.junbuks); s_cities.add(R.drawable.jejus);
        imageViews=new ArrayList<>();
        imageViews.add(homebinding.i1); imageViews.add(homebinding.i2); imageViews.add(homebinding.i3); imageViews.add(homebinding.i4);
        imageViews.add(homebinding.i5); imageViews.add(homebinding.i6); imageViews.add(homebinding.i7); imageViews.add(homebinding.i8);
        imageViews.add(homebinding.i9); imageViews.add(homebinding.i10); imageViews.add(homebinding.i11); imageViews.add(homebinding.i12);
        imageViews.add(homebinding.i13); imageViews.add(homebinding.i14); imageViews.add(homebinding.i15); imageViews.add(homebinding.i16);
    }
    public void setting_sliding(){
        Glide.with(context).load(R.drawable.seouls).into(homebinding.i1);
        Glide.with(context).load(R.drawable.incheon).into(homebinding.i2);
        Glide.with(context).load(R.drawable.daejun).into(homebinding.i3);
        Glide.with(context).load(R.drawable.gwangjoo).into(homebinding.i4);
        Glide.with(context).load(R.drawable.daegu).into(homebinding.i5);
        Glide.with(context).load(R.drawable.ulsan).into(homebinding.i6);
        Glide.with(context).load(R.drawable.pusan).into(homebinding.i7);
        Glide.with(context).load(R.drawable.gyeonggi).into(homebinding.i8);
        Glide.with(context).load(R.drawable.kangwon).into(homebinding.i9);
        Glide.with(context).load(R.drawable.chungnam).into(homebinding.i10);
        Glide.with(context).load(R.drawable.chungbuk).into(homebinding.i11);
        Glide.with(context).load(R.drawable.kyungnam).into(homebinding.i12);
        Glide.with(context).load(R.drawable.kyungbuk).into(homebinding.i13);
        Glide.with(context).load(R.drawable.junnam).into(homebinding.i14);
        Glide.with(context).load(R.drawable.junbuk).into(homebinding.i15);
        Glide.with(context).load(R.drawable.jeju).into(homebinding.i16);
        System.out.println("sliding");
        theme_top_fragment.themebinding.searchArrow.setOnClickListener(v -> {
            if(can_see_popular==1) {
                homebinding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        });
        theme_top_fragment.themebinding.changeContainer.setOnClickListener(v -> {
            if(can_see_popular==1) {
                homebinding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        });
        changing_image();
    }
    public void changing_image(){
        System.out.println("changing_image");
        for(int i=0; i<16; i++){
            if(i==city_select){
                Glide.with(context).load(s_cities.get(i)).into(imageViews.get(i));
            }else{
                Glide.with(context).load(cities.get(i)).into(imageViews.get(i));
            }
        }
        homebinding.i1.setOnClickListener(v -> {
            city_select=0;
            region="서울";
            for(int i=0; i<16; i++){
                if(i==city_select){
                    Glide.with(context).load(s_cities.get(i)).into(imageViews.get(i));
                }else{
                    Glide.with(context).load(cities.get(i)).into(imageViews.get(i));
                }
            }
            searchViewModel.get_popularlist(region);
            theme_fragment.setRegion(region);
            theme_fragment.themeViewModel.getPopularCarpingPlace(region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setString("region", region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setInt("region_num", city_select);
        });
        homebinding.i2.setOnClickListener(v -> {
            city_select=1;
            region="인천";
            for(int i=0; i<16; i++){
                if(i==city_select){
                    Glide.with(context).load(s_cities.get(i)).into(imageViews.get(i));
                }else{
                    Glide.with(context).load(cities.get(i)).into(imageViews.get(i));
                }
            }
            searchViewModel.get_popularlist(region);
            theme_fragment.setRegion(region);
            theme_fragment.themeViewModel.getPopularCarpingPlace(region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setString("region", region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setInt("region_num", city_select);
        });
        homebinding.i3.setOnClickListener(v -> {
            city_select=2;
            region="대전";
            for(int i=0; i<16; i++){
                if(i==city_select){
                    Glide.with(context).load(s_cities.get(i)).into(imageViews.get(i));
                }else{
                    Glide.with(context).load(cities.get(i)).into(imageViews.get(i));
                }
            }
            searchViewModel.get_popularlist(region);
            theme_fragment.setRegion(region);
            theme_fragment.themeViewModel.getPopularCarpingPlace(region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setString("region", region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setInt("region_num", city_select);
        });
        homebinding.i4.setOnClickListener(v -> {
            city_select=3;
            region="광주";
            for(int i=0; i<16; i++){
                if(i==city_select){
                    Glide.with(context).load(s_cities.get(i)).into(imageViews.get(i));
                }else{
                    Glide.with(context).load(cities.get(i)).into(imageViews.get(i));
                }
            }
            searchViewModel.get_popularlist(region);
            theme_fragment.setRegion(region);
            theme_fragment.themeViewModel.getPopularCarpingPlace(region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setString("region", region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setInt("region_num", city_select);
        });
        homebinding.i5.setOnClickListener(v -> {
            city_select=4;
            region="대구";
            for(int i=0; i<16; i++){
                if(i==city_select){
                    Glide.with(context).load(s_cities.get(i)).into(imageViews.get(i));
                }else{
                    Glide.with(context).load(cities.get(i)).into(imageViews.get(i));
                }
            }
            searchViewModel.get_popularlist(region);
            theme_fragment.setRegion(region);
            theme_fragment.themeViewModel.getPopularCarpingPlace(region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setString("region", region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setInt("region_num", city_select);
        });
        homebinding.i6.setOnClickListener(v -> {
            city_select=5;
            region="울산";
            for(int i=0; i<16; i++){
                if(i==city_select){
                    Glide.with(context).load(s_cities.get(i)).into(imageViews.get(i));
                }else{
                    Glide.with(context).load(cities.get(i)).into(imageViews.get(i));
                }
            }
            searchViewModel.get_popularlist(region);
            theme_fragment.setRegion(region);
            theme_fragment.themeViewModel.getPopularCarpingPlace(region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setString("region", region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setInt("region_num", city_select);
        });
        homebinding.i7.setOnClickListener(v -> {
            city_select=6;
            region="부산";
            for(int i=0; i<16; i++){
                if(i==city_select){
                    Glide.with(context).load(s_cities.get(i)).into(imageViews.get(i));
                }else{
                    Glide.with(context).load(cities.get(i)).into(imageViews.get(i));
                }
            }
            searchViewModel.get_popularlist(region);
            theme_fragment.setRegion(region);
            theme_fragment.themeViewModel.getPopularCarpingPlace(region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setString("region", region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setInt("region_num", city_select);
        });
        homebinding.i8.setOnClickListener(v -> {
            city_select=7;
            region="경기도";
            for(int i=0; i<16; i++){
                if(i==city_select){
                    Glide.with(context).load(s_cities.get(i)).into(imageViews.get(i));
                }else{
                    Glide.with(context).load(cities.get(i)).into(imageViews.get(i));
                }
            }
            searchViewModel.get_popularlist(region);
            theme_fragment.setRegion(region);
            theme_fragment.themeViewModel.getPopularCarpingPlace(region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setString("region", region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setInt("region_num", city_select);
        });
        homebinding.i9.setOnClickListener(v -> {
            city_select=8;
            region="강원도";
            for(int i=0; i<16; i++){
                if(i==city_select){
                    Glide.with(context).load(s_cities.get(i)).into(imageViews.get(i));
                }else{
                    Glide.with(context).load(cities.get(i)).into(imageViews.get(i));
                }
            }
            searchViewModel.get_popularlist(region);
            theme_fragment.setRegion(region);
            theme_fragment.themeViewModel.getPopularCarpingPlace(region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setString("region", region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setInt("region_num", city_select);
        });
        homebinding.i10.setOnClickListener(v -> {
            city_select=9;
            region="충청남도";
            for(int i=0; i<16; i++){
                if(i==city_select){
                    Glide.with(context).load(s_cities.get(i)).into(imageViews.get(i));
                }else{
                    Glide.with(context).load(cities.get(i)).into(imageViews.get(i));
                }
            }
            searchViewModel.get_popularlist(region);
            theme_fragment.setRegion(region);
            theme_fragment.themeViewModel.getPopularCarpingPlace(region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setString("region", region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setInt("region_num", city_select);
        });
        homebinding.i11.setOnClickListener(v -> {
            city_select=10;
            region="충청북도";
            for(int i=0; i<16; i++){
                if(i==city_select){
                    Glide.with(context).load(s_cities.get(i)).into(imageViews.get(i));
                }else{
                    Glide.with(context).load(cities.get(i)).into(imageViews.get(i));
                }
            }
            searchViewModel.get_popularlist(region);
            theme_fragment.setRegion(region);
            theme_fragment.themeViewModel.getPopularCarpingPlace(region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setString("region", region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setInt("region_num", city_select);
        });
        homebinding.i12.setOnClickListener(v -> {
            city_select=11;
            region="경상남도";
            for(int i=0; i<16; i++){
                if(i==city_select){
                    Glide.with(context).load(s_cities.get(i)).into(imageViews.get(i));
                }else{
                    Glide.with(context).load(cities.get(i)).into(imageViews.get(i));
                }
            }
            searchViewModel.get_popularlist(region);
            theme_fragment.setRegion(region);
            theme_fragment.themeViewModel.getPopularCarpingPlace(region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setString("region", region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setInt("region_num", city_select);
        });
        homebinding.i13.setOnClickListener(v -> {
            city_select=12;
            region="경상북도";
            for(int i=0; i<16; i++){
                if(i==city_select){
                    Glide.with(context).load(s_cities.get(i)).into(imageViews.get(i));
                }else{
                    Glide.with(context).load(cities.get(i)).into(imageViews.get(i));
                }
            }
            searchViewModel.get_popularlist(region);
            theme_fragment.setRegion(region);
            theme_fragment.themeViewModel.getPopularCarpingPlace(region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setString("region", region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setInt("region_num", city_select);
        });
        homebinding.i14.setOnClickListener(v -> {
            city_select=13;
            region="전라남도";
            for(int i=0; i<16; i++){
                if(i==city_select){
                    Glide.with(context).load(s_cities.get(i)).into(imageViews.get(i));
                }else{
                    Glide.with(context).load(cities.get(i)).into(imageViews.get(i));
                }
            }
            searchViewModel.get_popularlist(region);
            theme_fragment.setRegion(region);
            theme_fragment.themeViewModel.getPopularCarpingPlace(region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setString("region", region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setInt("region_num", city_select);
        });
        homebinding.i15.setOnClickListener(v -> {
            city_select=14;
            region="전라북도";
            for(int i=0; i<16; i++){
                if(i==city_select){
                    Glide.with(context).load(s_cities.get(i)).into(imageViews.get(i));
                }else{
                    Glide.with(context).load(cities.get(i)).into(imageViews.get(i));
                }
            }
            searchViewModel.get_popularlist(region);
            theme_fragment.setRegion(region);
            theme_fragment.themeViewModel.getPopularCarpingPlace(region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setString("region", region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setInt("region_num", city_select);
        });
        homebinding.i16.setOnClickListener(v -> {
            city_select=15;
            region="제주";
            for(int i=0; i<16; i++){
                if(i==city_select){
                    Glide.with(context).load(s_cities.get(i)).into(imageViews.get(i));
                }else{
                    Glide.with(context).load(cities.get(i)).into(imageViews.get(i));
                }
            }
            searchViewModel.get_popularlist(region);
            theme_fragment.setRegion(region);
            theme_fragment.themeViewModel.getPopularCarpingPlace(region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setString("region", region);
            SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).setInt("region_num", city_select);
        });
    }
    public void setting_searchadapter(){
        homebinding.popularRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        searchAdapter=searchViewModel.setting_adapter();
        homebinding.popularRecyclerview.setAdapter(searchAdapter);
    }
    public void starting_observe_change_popular(){
        searchViewModel.change_popular.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==1){
                    theme_top_fragment.changing_region(region, searchViewModel.populars);
                }
            }
        });
    }
    public void setting_search_text(){
        homebinding.searchBar.setOnClickListener(v -> {
            Intent intent=new Intent(context, MainSearchWith.class);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        region=SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).getString("region", "서울");
        city_select=SharedPreferenceManager.getInstance(getActivity().getApplicationContext()).getInt("region_num", 0);
        System.out.println("onResume");
        System.out.println(region+" "+city_select);
        setting_sliding();
    }
}
