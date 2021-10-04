package com.tourkakao.carping.Map.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.BuildConfig;
import com.tourkakao.carping.Map.Adapter.MapSearchAdapter;
import com.tourkakao.carping.Map.Adapter.MapSearchFromApiAdapter;
import com.tourkakao.carping.Map.Adapter.MapSearchTourAdapter;
import com.tourkakao.carping.Map.DataClass.MapSearch;
import com.tourkakao.carping.Map.DataClass.MapSearchFromApi;
import com.tourkakao.carping.Map.viewmodel.MapViewModel;
import com.tourkakao.carping.NetworkwithToken.MapInterface;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityMapSearchBinding;
import com.tourkakao.carping.registernewcarping.DataClass.CarpingSearchKeyword;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapSearchActivity extends AppCompatActivity {
    ActivityMapSearchBinding searchBinding;
    private String KAKAO_KEY= "KakaoAK "+ BuildConfig.KAKAO_REST_API_KEY;
    Context context;
    int cat;
    String keyword, x, y, category_code;
    Retrofit retrofit=null;
    MapSearchAdapter adapter;
    MapSearchFromApiAdapter apiAdapter;
    MapSearchTourAdapter tourAdapter;
    ArrayList<MapSearch.Place> list;
    ArrayList<MapSearchFromApi> apilist;
    MapViewModel mapViewModel;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchBinding=ActivityMapSearchBinding.inflate(getLayoutInflater());
        setContentView(searchBinding.getRoot());
        context=this;
        intent=getIntent();
        x=intent.getStringExtra("lat");
        y=intent.getStringExtra("lon");

        mapViewModel=new ViewModelProvider(this).get(MapViewModel.class);
        mapViewModel.setContext(context);
        mapViewModel.setMylat(Float.parseFloat(x));
        mapViewModel.setMylon(Float.parseFloat(y));
        mapViewModel.setThis_activity(this);

        setting_data();
    }
    public void setting_data(){
        cat=intent.getIntExtra("category", 1001);
        if(cat==1001){
            keyword="화장실";
            searchBinding.title.setText("화장실");
            searchBinding.textToolbar.setVisibility(View.VISIBLE);
            searchBinding.searchToolbar.setVisibility(View.GONE);
            search_bath_by_keyword();
        }else if(cat==1002){
            category_code="CS2";
            searchBinding.title.setText("편의점");
            searchBinding.textToolbar.setVisibility(View.VISIBLE);
            searchBinding.searchToolbar.setVisibility(View.GONE);
            search_conv_by_category();
        }else if(cat==1003){
            category_code="PK6";
            searchBinding.title.setText("주차장");
            searchBinding.textToolbar.setVisibility(View.VISIBLE);
            searchBinding.searchToolbar.setVisibility(View.GONE);
            search_parking_by_category();
        }else if(cat==1004){
            searchBinding.title.setText("차박지");
            searchBinding.textToolbar.setVisibility(View.VISIBLE);
            searchBinding.searchToolbar.setVisibility(View.GONE);
            setting_search_fromapi_recyclerview();
            mapViewModel.getting_carping();
        }else if(cat==1005){
            searchBinding.textToolbar.setVisibility(View.GONE);
            searchBinding.searchToolbar.setVisibility(View.VISIBLE);
            setting_search_from_tour_recyclerview();
            setting_search_edittext();
        }
    }
    public void search_bath_by_keyword(){
        Gson gson=new GsonBuilder().setLenient().create();
        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://dapi.kakao.com/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        MapInterface api=retrofit.create(MapInterface.class);
        api.getSearchKeyword(KAKAO_KEY, keyword, y, x, 10000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MapSearch>() {
                    @Override
                    public void onSuccess(@NonNull MapSearch mapSearch) {
                        if(mapSearch.getDocuments().size()==0){
                            Toast.makeText(context, "주변에 화장실이 없어요", Toast.LENGTH_SHORT).show();
                        }else{
                            setData(mapSearch.getDocuments());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
    public void search_conv_by_category(){
        Gson gson=new GsonBuilder().setLenient().create();
        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://dapi.kakao.com/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        MapInterface api=retrofit.create(MapInterface.class);
        api.getSearchCategory(KAKAO_KEY, category_code, y, x, 10000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MapSearch>() {
                    @Override
                    public void onSuccess(@NonNull MapSearch mapSearch) {
                        if(mapSearch.getDocuments().size()==0){
                            Toast.makeText(context, "주변 편의점이 없어요", Toast.LENGTH_SHORT).show();
                        }else {
                            setData(mapSearch.getDocuments());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
    public void search_parking_by_category(){
        Gson gson=new GsonBuilder().setLenient().create();
        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://dapi.kakao.com/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        MapInterface api=retrofit.create(MapInterface.class);
        api.getSearchCategory(KAKAO_KEY, category_code, y, x, 10000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MapSearch>() {
                    @Override
                    public void onSuccess(@NonNull MapSearch mapSearch) {
                        if(mapSearch.getDocuments().size()==0){
                            Toast.makeText(context, "주변 주차장이 없어요", Toast.LENGTH_SHORT).show();
                        }else {
                            setData(mapSearch.getDocuments());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
    public void setData(List data){
        Gson gson=new Gson();
        String total=gson.toJson(data);
        Type type=new TypeToken<ArrayList<MapSearch.Place>>(){}.getType();
        list=gson.fromJson(total, type);
        if(list.size()!=0) {
            Collections.sort(list);
            setting_search_recyclerview(list);
        }
    }
    public void setting_search_recyclerview(ArrayList<MapSearch.Place> list){
        searchBinding.mapsearchRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        adapter=new MapSearchAdapter(context, list);
        searchBinding.mapsearchRecyclerview.setAdapter(adapter);
        adapter.setOnSelectItemCLickListener(new MapSearchAdapter.OnSelectItemClickListener() {
            @Override
            public void OnSelectItemClick(View v, int pos) {
                Intent returnintent=new Intent();
                returnintent.putExtra("lat", list.get(pos).getY());
                returnintent.putExtra("lon", list.get(pos).getX());
                returnintent.putExtra("name", list.get(pos).getPlace_name());
                returnintent.putExtra("address", list.get(pos).getAddress_name());
                returnintent.putExtra("category", list.get(pos).getCategory_name());
                returnintent.putExtra("distance", list.get(pos).getDistance());
                setResult(RESULT_OK, returnintent);
                finish();
            }
        });
    }
    public void setting_search_fromapi_recyclerview(){
        searchBinding.mapsearchRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        apiAdapter=mapViewModel.setting_adpater();
        searchBinding.mapsearchRecyclerview.setAdapter(apiAdapter);
        apiAdapter.setOnSelectItemCLickListener(new MapSearchFromApiAdapter.OnSelectItemClickListener() {
            @Override
            public void OnSelectItemClick(View v, int pos) {
                Intent returnintent=new Intent();
                returnintent.putExtra("lat", mapViewModel.lists.get(pos).getLatitude());
                returnintent.putExtra("lon", mapViewModel.lists.get(pos).getLongitude());
                returnintent.putExtra("name", mapViewModel.lists.get(pos).getTitle());
                returnintent.putExtra("address", mapViewModel.lists.get(pos).getAddress());
                returnintent.putExtra("category", "차박지");
                returnintent.putExtra("distance", mapViewModel.lists.get(pos).getDistance());
                returnintent.putExtra("image", mapViewModel.lists.get(pos).getImage());
                setResult(RESULT_OK, returnintent);
                finish();
            }
        });
    }
    public void setting_search_from_tour_recyclerview(){
        searchBinding.mapsearchRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        tourAdapter=mapViewModel.setting_touradpater();
        searchBinding.mapsearchRecyclerview.setAdapter(tourAdapter);
        tourAdapter.setOnSelectItemCLickListener(new MapSearchTourAdapter.OnSelectItemClickListener() {
            @Override
            public void OnSelectItemClick(View v, int pos) {
                Intent returnintent=new Intent();
                returnintent.putExtra("lat", mapViewModel.tours.get(pos).getLat());
                returnintent.putExtra("lon", mapViewModel.tours.get(pos).getLon());
                returnintent.putExtra("name", mapViewModel.tours.get(pos).getName());
                returnintent.putExtra("address", mapViewModel.tours.get(pos).getAddress());
                returnintent.putExtra("category", "관광지");
                returnintent.putExtra("distance", mapViewModel.tours.get(pos).getDistance());
                returnintent.putExtra("image", mapViewModel.tours.get(pos).getImage());
                setResult(RESULT_OK, returnintent);
                finish();
            }
        });
    }
    public void setting_search_edittext(){
        searchBinding.campingSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mapViewModel.getting_tour(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
}