package com.tourkakao.carping.MainSearch.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.GpsLocation.GpsTracker;
import com.tourkakao.carping.MainSearch.Adapter.MainSearchAdapter;
import com.tourkakao.carping.MainSearch.Adapter.RecentAdapter;
import com.tourkakao.carping.MainSearch.ViewModel.MainSearchViewModel;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityMainSearchWithBinding;
import com.tourkakao.carping.theme.Activity.ThemeDetailActivity;

import java.util.ArrayList;


public class MainSearchWith extends AppCompatActivity {
    ActivityMainSearchWithBinding searchWithBinding;
    Context context;
    MainSearchViewModel mainSearchViewModel;
    RecentAdapter recentAdapter;
    MainSearchAdapter mainSearchAdapter;
    String keyword="";
    double mylat, mylon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchWithBinding = ActivityMainSearchWithBinding.inflate(getLayoutInflater());
        setContentView(searchWithBinding.getRoot());
        context = this;

        Glide.with(context).load(R.drawable.back).into(searchWithBinding.back);
        Glide.with(context).load(R.drawable.search_remove_img).into(searchWithBinding.remove);

        mainSearchViewModel=new ViewModelProvider(this).get(MainSearchViewModel.class);
        mainSearchViewModel.setContext(context);

        getting_mylocate();
        starting_observe_popular();
        setting_recent_recyclerview();
        setting_search_recyclerview();
        setting_search();
    }
    public void getting_mylocate(){
        GpsTracker gpsTracker=new GpsTracker(context);
        gpsTracker.getLocation();
        mylat=gpsTracker.getLatitude();
        mylon=gpsTracker.getLongitude();
        gpsTracker.stopUsingGPS();
    }
    public void starting_observe_popular(){
        mainSearchViewModel.populars.observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                searchWithBinding.popularContainer.removeAllViews();
                for (int i = 0; i < strings.size(); i++) {
                    TextView popular = new TextView(context);
                    popular.setText(strings.get(i));
                    popular.setBackgroundResource(R.drawable.purple_border_round);
                    popular.setPadding(60, 30, 60, 30);
                    popular.setTextColor(Color.parseColor("#9F81F7"));
                    popular.setClickable(true);
                    int finalI = i;
                    popular.setOnClickListener(v -> {
                        keyword=strings.get(finalI);
                        searchWithBinding.mainSearch.setText(keyword);
                        mainSearchViewModel.searching(keyword, mylat, mylon);
                    });
                    searchWithBinding.popularContainer.addView(popular);
                }
            }
        });
    }
    public void setting_recent_recyclerview(){
        searchWithBinding.recentRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        recentAdapter=mainSearchViewModel.setting_recentadapter();
        searchWithBinding.recentRecyclerview.setAdapter(recentAdapter);
        recentAdapter.setOnSelectItemCLickListener(new RecentAdapter.OnSelectItemClickListener() {
            @Override
            public void OnSelectItemClick(View v, int pos, String word) {
                keyword=word;
                searchWithBinding.mainSearch.setText(keyword);
                mainSearchViewModel.searching(keyword, mylat, mylon);
            }
        });
    }
    public void setting_search(){
        searchWithBinding.mainSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keyword=s.toString();
                if(keyword.length()==0){
                    searchWithBinding.mainSearchRecyclerview.setVisibility(View.GONE);
                }else {
                    searchWithBinding.mainSearchRecyclerview.setVisibility(View.VISIBLE);
                    mainSearchViewModel.searching(keyword, mylat, mylon);
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        searchWithBinding.remove.setOnClickListener(v -> {
            keyword="";
            searchWithBinding.mainSearch.setText("");
        });
    }
    public void setting_search_recyclerview(){
        searchWithBinding.mainSearchRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        mainSearchAdapter=mainSearchViewModel.setting_mainadapter();
        searchWithBinding.mainSearchRecyclerview.setAdapter(mainSearchAdapter);
        mainSearchAdapter.setOnSelectItemCLickListener(new MainSearchAdapter.OnSelectItemClickListener() {
            @Override
            public void OnSelectItemClick(View v, int pos, int pk) {
                String name=mainSearchViewModel.searches.get(pos).getName();
                mainSearchViewModel.search_complete(keyword, name);
                Intent intent=new Intent(context, ThemeDetailActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("pk", pk);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        mainSearchViewModel.get_popular_and_recent();
    }
}