package com.tourkakao.carping.registernewcarping.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.BuildConfig;
import com.tourkakao.carping.NetworkwithToken.RegisterInterface;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivitySearchNewCarpingBinding;
import com.tourkakao.carping.registernewcarping.Adapter.LocationInfoAdapter;
import com.tourkakao.carping.registernewcarping.DataClass.CarpingSearchKeyword;
import com.tourkakao.carping.registernewcarping.viewmodel.SearchViewmodel;

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

public class SearchNewCarpingActivity extends AppCompatActivity {
    private ActivitySearchNewCarpingBinding searchNewCarpingBinding;
    Context context;
    SearchViewmodel searchViewmodel;
    String search_text=null;
    private String KAKAO_KEY= "KakaoAK "+BuildConfig.KAKAO_REST_API_KEY;
    private Retrofit retrofit=null;
    private MutableLiveData<ArrayList<CarpingSearchKeyword.Place>> places=new MutableLiveData<>();
    private LocationInfoAdapter adapter=null;
    private MapPOIItem marker;
    private MapView mapView;
    private MapPoint mapPoint;
    Point size;
    int screen_width;
    String place_name=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchNewCarpingBinding=ActivitySearchNewCarpingBinding.inflate(getLayoutInflater());
        setContentView(searchNewCarpingBinding.getRoot());
        context=this;

        searchViewmodel=new ViewModelProvider(this).get(SearchViewmodel.class);
        searchViewmodel.setContext(context);
        searchViewmodel.getting_registered_carping_place();

        size=new Point();
        getWindowManager().getDefaultDisplay().getRealSize(size);
        screen_width=size.x;

        setting_mapview();
        setting_searchview();
        setting_location_recyclerview();
        setting_complete();
        setting_back_button();
        starting_observe_isduplicate_carping_place();
    }

    public void setting_mapview(){
        mapView=new MapView(this);
        searchNewCarpingBinding.mapView.addView(mapView);
        mapPoint=MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633);
        mapView.setMapCenterPointAndZoomLevel(mapPoint, 4, true);
        marker=new MapPOIItem();
        marker.setItemName("기본");
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setCustomImageAutoscale(false);
        mapView.addPOIItem(marker);
    }
    public void setting_searchview(){
        searchNewCarpingBinding.searchview.setIconified(false);
        searchNewCarpingBinding.searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search_text=query;
                searchKeyword(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                search_text=null;
                return false;
            }
        });
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
        RegisterInterface api=retrofit.create(RegisterInterface.class);
        api.getSearchKeyword(KAKAO_KEY, keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CarpingSearchKeyword>() {
                    @Override
                    public void onSuccess(@NonNull CarpingSearchKeyword carpingSearchKeyword) {
                        if(carpingSearchKeyword.getDocuments().size()==0){
                            Toast.makeText(context, "검색 결과가 없어요", Toast.LENGTH_SHORT).show();
                            search_text=null;
                        }else {
                            setData(carpingSearchKeyword.getDocuments());
                        }
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(context, "검색 결과가 없어요", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void setData(List data){
        Gson gson=new Gson();
        String total=gson.toJson(data);
        Type type=new TypeToken<ArrayList<CarpingSearchKeyword.Place>>(){}.getType();
        ArrayList<CarpingSearchKeyword.Place> list=gson.fromJson(total, type);
        places.setValue(list);
    }
    public void setting_location_recyclerview(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        searchNewCarpingBinding.locationView.setLayoutManager(layoutManager);
        PagerSnapHelper snapHelper=new PagerSnapHelper();
        snapHelper.attachToRecyclerView(searchNewCarpingBinding.locationView);
        places.observe(this, new Observer<ArrayList<CarpingSearchKeyword.Place>>() {
            @Override
            public void onChanged(ArrayList<CarpingSearchKeyword.Place> places) {
                if(adapter==null){
                    adapter=new LocationInfoAdapter(context, places, screen_width);
                    searchNewCarpingBinding.locationView.setAdapter(adapter);
                }else{
                    adapter.update_Item(places);
                }
                searchNewCarpingBinding.locationView.scrollToPosition(0);
                setting_marker_on_map(0);
            }
        });
        searchNewCarpingBinding.locationView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@androidx.annotation.NonNull RecyclerView recyclerView, int dx, int dy) {
                View view=snapHelper.findSnapView(searchNewCarpingBinding.locationView.getLayoutManager());
                if(view!=null) {
                    int position = searchNewCarpingBinding.locationView.getLayoutManager().getPosition(view);
                    setting_marker_on_map(position);
                }else{
                    search_text=null;
                }
            }
        });
    }
    public void setting_marker_on_map(int position){
        CarpingSearchKeyword.Place place = adapter.getItem(position);
        mapPoint = MapPoint.mapPointWithGeoCoord(Double.parseDouble(place.getY()), Double.parseDouble(place.getX()));
        mapView.setMapCenterPointAndZoomLevel(mapPoint, 4, true);
        mapView.removePOIItem(marker);
        marker.setItemName(place.getPlace_name());
        place_name=place.getPlace_name();
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        marker.setCustomImageResourceId(R.drawable.mycarping_marker);
        marker.setCustomImageAutoscale(false);
        mapView.addPOIItem(marker);
    }
    public void setting_complete(){
        searchNewCarpingBinding.finishSearchBtn.setOnClickListener(v -> {
            if(search_text==null){
                Toast.makeText(context, "차박지를 검색해주세요", Toast.LENGTH_SHORT).show();
            }else {
                searchViewmodel.check_isduplicate(Math.round(mapPoint.getMapPointGeoCoord().latitude*100000)/100000.0, Math.round(mapPoint.getMapPointGeoCoord().longitude*100000)/100000.0);
            }
        });
    }
    public void starting_observe_isduplicate_carping_place(){
        searchViewmodel.isduplicate.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==1){
                    Dialog dialog=new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.duplicate_carping_alertdialog);
                    Button ok=dialog.findViewById(R.id.okbtn);
                    ImageView image=dialog.findViewById(R.id.duplicate_img);
                    Glide.with(context).load(R.drawable.duplicate_img).into(image);
                    ok.setOnClickListener(v -> {
                        dialog.dismiss();
                    });
                    dialog.show();
                }else if(integer==0){
                    Intent intent = new Intent();
                    intent.putExtra("place", place_name);
                    intent.putExtra("lat", Double.toString(mapPoint.getMapPointGeoCoord().latitude));
                    intent.putExtra("lon", Double.toString(mapPoint.getMapPointGeoCoord().longitude));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
    public void setting_back_button(){
        searchNewCarpingBinding.back.setOnClickListener(v -> {
            Intent intent=new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        searchNewCarpingBinding.mapView.removeView(mapView);
    }
}