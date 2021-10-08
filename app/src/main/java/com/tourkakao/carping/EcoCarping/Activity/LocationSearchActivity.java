package com.tourkakao.carping.EcoCarping.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.BuildConfig;
import com.tourkakao.carping.EcoCarping.Adapter.LocationInfoAdapter;
import com.tourkakao.carping.EcoCarping.DTO.ResultSearchKeyword;
import com.tourkakao.carping.NetworkwithToken.EcoInterface;
import com.tourkakao.carping.R;
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
    private String placeName;
    private Toast myToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getApplicationContext();

        ecobinding= ActivityLocationSearchBinding.inflate(getLayoutInflater());
        setContentView(ecobinding.getRoot());

        ecobinding.searchBarArea.bringToFront();
        initLayout();
        settingMapView();

        settingLocationList();

        ecobinding.searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH) {
                    searchKeyword(ecobinding.searchBar.getText().toString());
                    return true;
                }
                return false;
            }
        });

        ecobinding.completion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ecobinding.searchBar.getText().length()==0){
                    myToast = Toast.makeText(getApplicationContext(),"장소를 검색해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                Intent intent=getIntent();
                intent.putExtra("place",placeName);
                intent.putExtra("x",mapPoint.getMapPointGeoCoord().longitude);
                intent.putExtra("y",mapPoint.getMapPointGeoCoord().latitude);
                ecobinding.mapView.removeView(mapView);
                setResult(2, intent);
                finish();
            }
        });
    }

    public void initLayout(){
        Glide.with(getApplicationContext()).load(R.drawable.back).into(ecobinding.back);
        Glide.with(getApplicationContext()).load(R.drawable.search_remove_img).into(ecobinding.deleteText);
        ecobinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=getIntent();
                ecobinding.mapView.removeView(mapView);
                setResult(3, intent);
                finish();
            }
        });
        ecobinding.deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ecobinding.searchBar.setText("");
                ecobinding.locationView.setAdapter(null);
                mapView.removePOIItem(marker);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=getIntent();
        ecobinding.mapView.removeView(mapView);
        setResult(3, intent);
        finish();
    }

    public void settingMapView(){
        mapView=new MapView(this);
        mapView.setDaumMapApiKey(KAKAO_KEY);
        ecobinding.mapView.addView(mapView);
        marker = new MapPOIItem();
        mapPoint=MapPoint.mapPointWithGeoCoord(37.5642135, 127.0016985);
        mapView.setMapCenterPoint(mapPoint,true);
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        mapView.addPOIItem(marker);
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
        ecobinding.locationView.setOffscreenPageLimit(3);
        ecobinding.locationView.setClipToPadding(false);
        ecobinding.locationView.setPadding(100,0,100,0);
        places.observe(this, new Observer<ArrayList<ResultSearchKeyword.Place>>() {
            @Override
            public void onChanged(ArrayList<ResultSearchKeyword.Place> places) {
                if((places.size())==0){
                    myToast = Toast.makeText(getApplicationContext(),"검색 결과가 없습니다", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                adapter=new LocationInfoAdapter(context,places);
                ecobinding.locationView.setAdapter(adapter);
            }
        });
        ecobinding.locationView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mapView.removePOIItem(marker);
                ResultSearchKeyword.Place place=adapter.getItem(position);
                placeName=place.getPlace_name();
                mapPoint=MapPoint.mapPointWithGeoCoord(Double.parseDouble(place.getY()),Double.parseDouble(place.getX()));
                mapView.setMapCenterPoint(mapPoint,true);
                marker.setItemName(place.getPlace_name());
                marker.setMapPoint(mapPoint);

                mapView.addPOIItem(marker);
            }
        });

    }
}