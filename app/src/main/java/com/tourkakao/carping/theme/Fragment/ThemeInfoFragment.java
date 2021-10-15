package com.tourkakao.carping.theme.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.BuildConfig;
import com.tourkakao.carping.GpsLocation.GpsTracker;
import com.tourkakao.carping.NetworkwithToken.ThemeInterface;
import com.tourkakao.carping.R;
import com.tourkakao.carping.theme.Activity.ThemeDetailActivity;
import com.tourkakao.carping.theme.Adapter.BlogAdapter;
import com.tourkakao.carping.theme.Dataclass.DaumBlog;
import com.tourkakao.carping.databinding.ThemeInfoFragmentBinding;
import com.tourkakao.carping.theme.viewmodel.ThemeDetailViewModel;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThemeInfoFragment extends Fragment {
    public ThemeInfoFragmentBinding infoFragmentBinding;
    ThemeDetailViewModel detailViewModel;
    private String KAKAO_KEY= "KakaoAK "+BuildConfig.KAKAO_REST_API_KEY;
    private Retrofit retrofit;
    Context context;
    String name; int pk;
    private BlogAdapter blogAdapter;
    ArrayList<DaumBlog.Blog> blogs=new ArrayList<>();

    public MapView mapView;
    MapPoint mapPoint=null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        infoFragmentBinding=ThemeInfoFragmentBinding.inflate(inflater, container, false);
        context=getContext();
        GpsTracker gpsTracker=new GpsTracker(context);
        detailViewModel= ((ThemeDetailActivity)getActivity()).detailViewModel;
        detailViewModel.setContext(context);
        infoFragmentBinding.setLifecycleOwner(this);
        infoFragmentBinding.setThemedetailviewmodel(detailViewModel);

        Glide.with(context).load(R.drawable.locate_img).into(infoFragmentBinding.locateImg);
        Glide.with(context).load(R.drawable.themedetail_reserve_img).into(infoFragmentBinding.themeReserveImg);
        Glide.with(context).load(R.drawable.themedetail_fire_img).into(infoFragmentBinding.themeFireImg);
        Glide.with(context).load(R.drawable.themedetail_pet_img).into(infoFragmentBinding.themePetImg);

        detailViewModel.getting_themedetail(pk, gpsTracker.getLatitude(), gpsTracker.getLongitude());
        starting_observe_lat_and_lon();
        gpsTracker.stopUsingGPS();

        setting_map();

        return infoFragmentBinding.getRoot();
    }
    public void setName(String name){
        this.name=name;
    }
    public void setPk(int pk){
        this.pk=pk;
    }
    public void setting_map(){
        if(mapView==null) {
            mapView = new MapView(context);
            infoFragmentBinding.mapView.addView(mapView);
            mapView.setOnDragListener(null);
        }
    }
    public void searchblog(){
        Gson gson=new GsonBuilder().setLenient().create();
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl("https://dapi.kakao.com/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
            ThemeInterface api=retrofit.create(ThemeInterface.class);
            api.getblog(KAKAO_KEY, name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<DaumBlog>() {
                        @Override
                        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull DaumBlog daumBlog) {
                            if(daumBlog.getDocuments().size()==0){
                                System.out.println("empty");
                            }else{
                                setData(daumBlog.getDocuments());
                            }
                        }
                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                            System.out.println(e);
                        }
                    });
        }
    }
    public void setData(List data) {
        Gson gson = new Gson();
        String total = gson.toJson(data);
        Type type=new TypeToken<ArrayList<DaumBlog.Blog>>(){}.getType();
        blogs=gson.fromJson(total, type);
        setting_blog_recyclerview();
    }
    public void setting_blog_recyclerview(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        infoFragmentBinding.themeSearchRecyclerview.setLayoutManager(layoutManager);
        blogAdapter=new BlogAdapter(context, blogs);
        blogAdapter.setOnSelectItemClickListener(new BlogAdapter.OnSelectItemClickListener() {
            @Override
            public void OnSelectItemClick(View v, int pos) {
                if(blogs.get(pos).getUrl()!=null) {
                    Intent intentUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(blogs.get(pos).getUrl()));
                    startActivity(intentUrl);
                }
            }
        });
        infoFragmentBinding.themeSearchRecyclerview.setAdapter(blogAdapter);
    }
    public void starting_observe_lat_and_lon(){
        detailViewModel.carping_lon.observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                float lan=detailViewModel.carping_lat.getValue();
                float lon=aFloat;
                MapPoint mapPoint=MapPoint.mapPointWithGeoCoord(lan, lon);
                mapView.setMapCenterPointAndZoomLevel(mapPoint, 3, true);
                MapPOIItem marker=new MapPOIItem();
                marker.setItemName(name);
                marker.setMapPoint(mapPoint);
                marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
                marker.setCustomImageResourceId(R.drawable.nowmarker);
                marker.setCustomImageAutoscale(false);
                mapView.addPOIItem(marker);
                mapView.clearFocus();
            }
        });
    }


}
