package com.tourkakao.carping.EcoCarping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.ViewGroup;

import com.tourkakao.carping.BuildConfig;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityEcoCarpingWriteBinding;
import com.tourkakao.carping.databinding.ActivityLocationSearchBinding;

import net.daum.mf.map.api.MapView;

public class LocationSearchActivity extends AppCompatActivity {
    ActivityLocationSearchBinding ecobinding;
    private String KAKAO_KEY= BuildConfig.KAKAO_NATIVE_APPKEY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ecobinding= ActivityLocationSearchBinding.inflate(getLayoutInflater());
        setContentView(ecobinding.getRoot());

        ecobinding.searchBar.setText(getIntent().getStringExtra("검색어"));
        settingToolbar();
        settingMapView();
    }

    public void settingToolbar(){
        Toolbar toolbar=ecobinding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void settingMapView(){
        MapView mapView=new MapView(this);
        ViewGroup mapViewContainer=ecobinding.mapView;
        mapView.setDaumMapApiKey(KAKAO_KEY);
        mapViewContainer.addView(mapView);

    }
}