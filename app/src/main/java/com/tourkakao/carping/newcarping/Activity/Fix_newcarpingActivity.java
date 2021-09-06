package com.tourkakao.carping.newcarping.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityFixNewcarpingBinding;
import com.tourkakao.carping.newcarping.viewmodel.FixNewCarpingViewModel;
import com.tourkakao.carping.registernewcarping.Activity.TagsActivity;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

public class Fix_newcarpingActivity extends AppCompatActivity {
    ActivityFixNewcarpingBinding fixNewcarpingBinding;
    FixNewCarpingViewModel viewModel;
    Context context;
    MapView mapView;
    int pk;
    private TextView addtag=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fixNewcarpingBinding=ActivityFixNewcarpingBinding.inflate(getLayoutInflater());
        setContentView(fixNewcarpingBinding.getRoot());
        context=this;

        viewModel=new ViewModelProvider(this).get(FixNewCarpingViewModel.class);
        initialize();
        setting_locate();
        setting_add_tags();
        setting_initial_tags();
    }
    public void setting_locate(){
        mapView=new MapView(this);
        fixNewcarpingBinding.mapView.addView(mapView);
        MapPoint mapPoint=MapPoint.mapPointWithGeoCoord(viewModel.lat, viewModel.lon);
        mapView.setMapCenterPointAndZoomLevel(mapPoint, 4, true);
        MapPOIItem marker=new MapPOIItem();
        marker.setItemName("위치");
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        mapView.addPOIItem(marker);
    }
    public void initialize(){
        Intent intent=getIntent();
        pk=intent.getIntExtra("id", 0);
        viewModel.lat=intent.getDoubleExtra("lat", 0.0);
        viewModel.lon=intent.getDoubleExtra("lon", 0.0);
        viewModel.f_title=intent.getStringExtra("title");
        viewModel.f_review=intent.getStringExtra("review");
        viewModel.f_tags=intent.getStringArrayListExtra("tags");
        fixNewcarpingBinding.titleEdittext.setText(viewModel.f_title);
        fixNewcarpingBinding.reviewEdittext.setText(viewModel.f_review);
        fixNewcarpingBinding.textCount.setText(viewModel.f_review.length()+"/100");
        viewModel.initial_images.add(intent.getStringExtra("image1"));
        viewModel.initial_images.add(intent.getStringExtra("image2"));
        viewModel.initial_images.add(intent.getStringExtra("image3"));
        viewModel.initial_images.add(intent.getStringExtra("image4"));
    }
    public void setting_add_tags(){
        addtag=new TextView(context);
        addtag.setText("+");
        addtag.setClickable(true);
        addtag.setBackgroundResource(R.drawable.purple_border_round);
        addtag.setPadding(60, 30, 60, 30);
        addtag.setOnClickListener(v -> {
            Intent intent=new Intent(context, TagsActivity.class);
            startActivityForResult(intent, 1004);
        });
        fixNewcarpingBinding.tagLayout.addView(addtag);
    }
    public void setting_initial_tags(){
        for(int i=0; i<viewModel.f_tags.size(); i++){
            TextView newtag=new TextView(context);
            newtag.setText("#"+viewModel.f_tags.get(i));
            newtag.setBackgroundResource(R.drawable.purple_border_round);
            newtag.setTextColor(Color.parseColor("#9F81F7"));
            newtag.setPadding(60, 30, 60, 30);
            fixNewcarpingBinding.tagLayout.addView(newtag);
        }
    }
}