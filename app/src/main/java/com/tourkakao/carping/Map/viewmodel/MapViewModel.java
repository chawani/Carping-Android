package com.tourkakao.carping.Map.viewmodel;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.text.AndroidCharacter;

import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.BuildConfig;
import com.tourkakao.carping.Map.Activity.MapSearchActivity;
import com.tourkakao.carping.Map.Adapter.MapSearchFromApiAdapter;
import com.tourkakao.carping.Map.Adapter.MapSearchTourAdapter;
import com.tourkakao.carping.Map.DataClass.MapSearchFromApi;
import com.tourkakao.carping.Map.DataClass.MapSearchTour;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MapViewModel extends ViewModel {
    Context context;
    float mylat, mylon;
    MapSearchFromApiAdapter adapter;
    MapSearchTourAdapter touradapter;
    public ArrayList<MapSearchFromApi> lists;
    public ArrayList<MapSearchTour> tours;
    MapSearchActivity this_activity;
    Geocoder geocoder;
    int pos=0;
    public MapViewModel(){
        lists=new ArrayList<>();
    }

    public void setContext(Context context) {
        this.context = context;
        geocoder=new Geocoder(context);
    }

    public void setThis_activity(MapSearchActivity this_activity) {
        this.this_activity = this_activity;
    }

    public void setMylon(float mylon) {
        this.mylon = mylon;
    }

    public void setMylat(float mylat) {
        this.mylat = mylat;
    }
    public MapSearchFromApiAdapter setting_adpater(){
        lists=new ArrayList<>();
        adapter=new MapSearchFromApiAdapter(context, lists);
        return adapter;
    }
    public MapSearchTourAdapter setting_touradpater(){
        tours=new ArrayList<>();
        touradapter=new MapSearchTourAdapter(context, tours);
        return touradapter;
    }
    public void getting_carping(){
        TotalApiClient.getMapApiService(context).get_carpingmap(mylat, mylon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        res -> {
                            if(res.isSuccess()){
                                Type type=new TypeToken<ArrayList<MapSearchFromApi>>(){}.getType();
                                String result=new Gson().toJson(res.getData(), type);
                                lists=new Gson().fromJson(result, type);
                                for(int i=0; i<lists.size(); i++){
                                    List<Address> addressslist=geocoder.getFromLocation(lists.get(i).getLatitude(), lists.get(i).getLongitude(), 1);
                                    if(addressslist!=null) {
                                        Address address = geocoder.getFromLocation(lists.get(i).getLatitude(), lists.get(i).getLongitude(), 1).get(0);
                                        lists.get(i).setAddress(address.getAddressLine(0));
                                    }else{
                                        lists.get(i).setAddress("주소 없음");
                                    }
                                }
                                adapter.updateItem(lists);
                            }
                        },
                        error -> {

                        }
                );

    }
    public void getting_tour(String keyword){
        System.out.println(keyword);
        if(keyword==null || keyword=="" || keyword==" "){
            tours=null;
            tours=new ArrayList<>();
            touradapter.updateItem(tours);
        }else {
            TotalApiClient.getMapApiService(context).search_tourmap(keyword, mylat, mylon)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            res -> {
                                if (res.isSuccess()) {
                                    Type type = new TypeToken<ArrayList<MapSearchTour>>() {
                                    }.getType();
                                    String result = new Gson().toJson(res.getData(), type);
                                    tours = new Gson().fromJson(result, type);
                                    touradapter.updateItem(tours);
                                }
                            },
                            error -> {

                            }
                    );
        }

    }
}
