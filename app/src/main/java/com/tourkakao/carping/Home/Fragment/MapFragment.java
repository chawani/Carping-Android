package com.tourkakao.carping.Home.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.GpsLocation.GpsTracker;
import com.tourkakao.carping.Map.Activity.MapSearchActivity;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.MapFragmentBinding;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import static android.app.Activity.RESULT_OK;

public class MapFragment extends Fragment {
    MapFragmentBinding mapbinding;
    Context context;
    MapView mapView;
    int to_another_page=0;
    int call_from_activityresult=0;
    double mylat, mylon;
    double nowlat, nowlon;
    GpsTracker gpsTracker;
    MapPoint nowpoint=null;
    MapPOIItem nowmarker=null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mapbinding=MapFragmentBinding.inflate(inflater, container, false);
        context=getActivity().getApplicationContext();

        nowlat=37.5642135;
        nowlon=127.0016985;
        Glide.with(context).load(R.drawable.bathroom_btn).into(mapbinding.bathroom);
        Glide.with(context).load(R.drawable.carping_btn).into(mapbinding.carping);
        Glide.with(context).load(R.drawable.conv_btn).into(mapbinding.conv);
        Glide.with(context).load(R.drawable.parking_btn).into(mapbinding.parking);

        getting_my_locate();
        setting_btn();

        return mapbinding.getRoot();
    }
    public void getting_my_locate(){
        gpsTracker=new GpsTracker(context);
        gpsTracker.getLocation();
        mylat=gpsTracker.getLatitude();
        mylon=gpsTracker.getLongitude();
        gpsTracker.stopUsingGPS();
    }
    public void setting_map(){
        if(mapbinding.mapView.getChildAt(0)==null) {
            mapView=null;
            mapView=new MapView(getContext());
            mapbinding.mapView.addView(mapView);
        }
        nowpoint=MapPoint.mapPointWithGeoCoord(nowlat, nowlon);
        mapView.setMapCenterPointAndZoomLevel(nowpoint, 1, true);
        if(nowmarker!=null){
            mapView.removePOIItem(nowmarker);
        }else{
            nowmarker=new MapPOIItem();
        }
        nowmarker.setItemName("검색장소");
        nowmarker.setMapPoint(nowpoint);
        nowmarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        mapView.addPOIItem(nowmarker);
    }
    public void setting_btn(){
        mapbinding.bathroom.setOnClickListener(v -> {
            Intent intent=new Intent(context, MapSearchActivity.class);
            intent.putExtra("category", 1001);
            intent.putExtra("lat", String.valueOf(mylat));
            intent.putExtra("lon", String.valueOf(mylon));
            startActivityForResult(intent, 1001);
        });
        mapbinding.conv.setOnClickListener(v -> {
            Intent intent=new Intent(context, MapSearchActivity.class);
            intent.putExtra("category", 1002);
            intent.putExtra("lat", String.valueOf(mylat));
            intent.putExtra("lon", String.valueOf(mylon));
            startActivityForResult(intent, 1002);
        });
        mapbinding.parking.setOnClickListener(v -> {
            Intent intent=new Intent(context, MapSearchActivity.class);
            intent.putExtra("category", 1003);
            intent.putExtra("lat", String.valueOf(mylat));
            intent.putExtra("lon", String.valueOf(mylon));
            startActivityForResult(intent, 1003);
        });
        mapbinding.carping.setOnClickListener(v -> {
            Intent intent=new Intent(context, MapSearchActivity.class);
            intent.putExtra("category", 1004);
            intent.putExtra("lat", String.valueOf(mylat));
            intent.putExtra("lon", String.valueOf(mylon));
            startActivityForResult(intent, 1004);
        });
        mapbinding.searchText.setOnClickListener(v -> {
            Intent intent=new Intent(context, MapSearchActivity.class);
            intent.putExtra("category", 1005);
            intent.putExtra("lat", String.valueOf(mylat));
            intent.putExtra("lon", String.valueOf(mylon));
            startActivityForResult(intent, 1005);
        });
    }
    public void setting_remove_map(){
        mapbinding.mapView.removeView(mapView);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==1001){
                nowlat=Double.parseDouble(data.getStringExtra("lat"));
                nowlon=Double.parseDouble(data.getStringExtra("lon"));
                mapbinding.searchText.setText(data.getStringExtra("name"));
                mapbinding.searchText.setTextColor(Color.BLACK);
                mapbinding.etcCardview.setVisibility(View.VISIBLE);
                mapbinding.apiEtcCardview.setVisibility(View.GONE);
                mapbinding.category.setText("화장실");
                mapbinding.name.setText(data.getStringExtra("name"));
                mapbinding.address.setText(data.getStringExtra("address"));
                mapbinding.distance.setText(Float.parseFloat(data.getStringExtra("distance"))/1000+"km");
                call_from_activityresult=1;
                setting_map();
            }else if(requestCode==1002){
                nowlat=Double.parseDouble(data.getStringExtra("lat"));
                nowlon=Double.parseDouble(data.getStringExtra("lon"));
                mapbinding.searchText.setText(data.getStringExtra("name"));
                mapbinding.searchText.setTextColor(Color.BLACK);
                mapbinding.etcCardview.setVisibility(View.VISIBLE);
                mapbinding.apiEtcCardview.setVisibility(View.GONE);
                mapbinding.category.setText("편의점");
                mapbinding.name.setText(data.getStringExtra("name"));
                mapbinding.address.setText(data.getStringExtra("address"));
                mapbinding.distance.setText(Float.parseFloat(data.getStringExtra("distance"))/1000+"km");
                call_from_activityresult=1;
                setting_map();
            }else if(requestCode==1003){
                nowlat=Double.parseDouble(data.getStringExtra("lat"));
                nowlon=Double.parseDouble(data.getStringExtra("lon"));
                mapbinding.searchText.setText(data.getStringExtra("name"));
                mapbinding.searchText.setTextColor(Color.BLACK);
                mapbinding.etcCardview.setVisibility(View.VISIBLE);
                mapbinding.apiEtcCardview.setVisibility(View.GONE);
                mapbinding.category.setText("주차장");
                mapbinding.name.setText(data.getStringExtra("name"));
                mapbinding.address.setText(data.getStringExtra("address"));
                mapbinding.distance.setText(Float.parseFloat(data.getStringExtra("distance"))/1000+"km");
                call_from_activityresult=1;
                setting_map();
            }else if(requestCode==1004){
                nowlat=data.getFloatExtra("lat", 0.0f);
                nowlon=data.getFloatExtra("lon", 0.0f);
                mapbinding.searchText.setText(data.getStringExtra("name"));
                mapbinding.searchText.setTextColor(Color.BLACK);
                mapbinding.etcCardview.setVisibility(View.GONE);
                mapbinding.apiEtcCardview.setVisibility(View.VISIBLE);
                mapbinding.apiCategory.setText("차박지");
                mapbinding.apiName.setText(data.getStringExtra("name"));
                mapbinding.apiAddress.setText(data.getStringExtra("address"));
                mapbinding.apidistance.setText(data.getDoubleExtra("distance", 0)+"km");
                if(data.getStringExtra("image")==null){
                    Glide.with(context).load(R.drawable.thema_no_img).transform(new RoundedCorners(10)).into(mapbinding.apiImage);
                }else {
                    Glide.with(context).load(data.getStringExtra("image")).transform(new RoundedCorners(10)).into(mapbinding.apiImage);
                }
                call_from_activityresult=1;
                setting_map();
            }else if(requestCode==1005){
                nowlat=data.getFloatExtra("lat", 0.0f);
                nowlon=data.getFloatExtra("lon", 0.0f);
                mapbinding.searchText.setText(data.getStringExtra("name"));
                mapbinding.searchText.setTextColor(Color.BLACK);
                mapbinding.etcCardview.setVisibility(View.GONE);
                mapbinding.apiEtcCardview.setVisibility(View.VISIBLE);
                mapbinding.apiCategory.setText("관광지");
                mapbinding.apiName.setText(data.getStringExtra("name"));
                mapbinding.apiAddress.setText(data.getStringExtra("address"));
                mapbinding.apidistance.setText(data.getDoubleExtra("distance", 0)+"km");
                if(data.getStringExtra("image")==null){
                    Glide.with(context).load(R.drawable.thema_no_img).transform(new RoundedCorners(10)).into(mapbinding.apiImage);
                }else {
                    Glide.with(context).load(data.getStringExtra("image")).transform(new RoundedCorners(10)).into(mapbinding.apiImage);
                }
                call_from_activityresult=1;
                setting_map();
            }
        }
    }
}
