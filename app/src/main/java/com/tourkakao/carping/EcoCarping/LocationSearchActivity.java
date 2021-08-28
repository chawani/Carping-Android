package com.tourkakao.carping.EcoCarping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.BuildConfig;
import com.tourkakao.carping.Home.EcoDataClass.EcoReview;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.EcoInterface;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityEcoCarpingWriteBinding;
import com.tourkakao.carping.databinding.ActivityLocationSearchBinding;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationSearchActivity extends AppCompatActivity {
    ActivityLocationSearchBinding ecobinding;
    private String KAKAO_KEY="KakaoAK "+BuildConfig.KAKAO_REST_API_KEY;
    private Context context;
    private Retrofit retrofit=null;
    private MutableLiveData<ArrayList<ResultSearchKeyword.Place>> places=new MutableLiveData<>();
    private LocationInfoAdapter adapter;
    private MapPOIItem marker;
    private MapView mapView;
    private MapPoint mapPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getApplicationContext();

        ecobinding= ActivityLocationSearchBinding.inflate(getLayoutInflater());
        setContentView(ecobinding.getRoot());

        ecobinding.searchBar.bringToFront();
        ecobinding.searchBar.setText(getIntent().getStringExtra("검색어"));
        settingToolbar();
        settingMapView();
        searchKeyword(ecobinding.searchBar.getText().toString());
        settingLocationList();

        ecobinding.completion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=getIntent();
                intent.putExtra("place",ecobinding.searchBar.getText().toString());
                intent.putExtra("x",mapPoint.getMapPointGeoCoord().longitude);
                intent.putExtra("y",mapPoint.getMapPointGeoCoord().latitude);
                setResult(2, intent);
                ecobinding.getRoot().removeView(ecobinding.mapView);
                finish();
            }
        });
    }

    public void settingToolbar(){
        Toolbar toolbar=ecobinding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void settingMapView(){
        mapView=new MapView(this);
        mapView.setDaumMapApiKey(KAKAO_KEY);
        ecobinding.mapView.addView(mapView);
        marker = new MapPOIItem();
    }

    public void searchKeyword(String keyword){
        Gson gson=new GsonBuilder().setLenient().create();

        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://dapi.kakao.com/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }

        EcoInterface api=retrofit.create(EcoInterface.class);
        api.getSearchKeyword(KAKAO_KEY,keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ResultSearchKeyword>() {
                    @Override
                    public void onSuccess(@NonNull ResultSearchKeyword resultSearchKeyword) {
                        setData(resultSearchKeyword.getDocuments());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("실패"+e.getMessage());
                    }
                });
    }

    public void setData(List data){
        Gson gson=new Gson();
        String totalDataString=gson.toJson(data);
        Type type=new TypeToken<ArrayList<ResultSearchKeyword.Place>>(){}.getType();
        ArrayList<ResultSearchKeyword.Place> list=gson.fromJson(totalDataString,type);
        places.setValue(list);
    }

    public void settingLocationList(){

        ecobinding.locationView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        PagerSnapHelper snapHelper=new PagerSnapHelper();
        snapHelper.attachToRecyclerView(ecobinding.locationView);
        places.observe(this, new Observer<ArrayList<ResultSearchKeyword.Place>>() {
            @Override
            public void onChanged(ArrayList<ResultSearchKeyword.Place> places) {
                adapter=new LocationInfoAdapter(context,places);
                ecobinding.locationView.setAdapter(adapter);
            }
        });
        ecobinding.locationView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@androidx.annotation.NonNull RecyclerView recyclerView, int dx, int dy) {
                View view=snapHelper.findSnapView(ecobinding.locationView.getLayoutManager());
                int position=ecobinding.locationView.getLayoutManager().getPosition(view);
                ResultSearchKeyword.Place place=adapter.getItem(position);
                ecobinding.searchBar.setText(place.getPlace_name());
                mapPoint=MapPoint.mapPointWithGeoCoord(Double.parseDouble(place.getY()),Double.parseDouble(place.getX()));
                mapView.setMapCenterPoint(mapPoint,true);
                marker.setItemName(place.getPlace_name());
                marker.setMapPoint(mapPoint);
                marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

                mapView.addPOIItem(marker);
            }
        });
    }
}