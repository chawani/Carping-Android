package com.tourkakao.carping.theme.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.BuildConfig;
import com.tourkakao.carping.NetworkwithToken.ThemeInterface;
import com.tourkakao.carping.databinding.ThemeRecommendFragmentBinding;
import com.tourkakao.carping.theme.Activity.ThemeDetailActivity;
import com.tourkakao.carping.theme.Adapter.RecommendAdapter;
import com.tourkakao.carping.theme.Dataclass.TourSearch;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThemeRecommendFragment extends Fragment {
    private ThemeRecommendFragmentBinding recommendFragmentBinding;
    private String KAKAO_KEY= "KakaoAK "+ BuildConfig.KAKAO_REST_API_KEY;
    Context context;
    public String lat, lon, category_code="AT4";
    String[] items={"관광지", "문화지", "음식점"};
    Retrofit retrofit=null;
    ArrayList<TourSearch.Place> list;
    RecommendAdapter recommendAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recommendFragmentBinding=ThemeRecommendFragmentBinding.inflate(inflater, container, false);

        context=getContext();

        setting_recyclerview();
        setting_spinner();
        return recommendFragmentBinding.getRoot();
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setting_recyclerview(){
        recommendFragmentBinding.recommendRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        list=new ArrayList<>();
        recommendAdapter=new RecommendAdapter(context, list);
        recommendFragmentBinding.recommendRecyclerview.setAdapter(recommendAdapter);
        recommendAdapter.setOnSelectItemCLickListener(new RecommendAdapter.OnSelectItemClickListener() {
            @Override
            public void OnSelectItemClick(View v, int pos) {
                String url=list.get(pos).getPlace_url();
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }
    public void setting_spinner(){
        ArrayAdapter<String> adapter=new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, items);
        recommendFragmentBinding.recommendSpinner.setAdapter(adapter);
        recommendFragmentBinding.recommendSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    category_code="AT4";
                    search_tour();
                }else if(position==1){
                    category_code="CT1";
                    search_culture();
                }else if(position==2){
                    category_code="FD6";
                    search_restaurant();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void search_tour(){
        Gson gson=new GsonBuilder().setLenient().create();
        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://dapi.kakao.com/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        ThemeInterface api=retrofit.create(ThemeInterface.class);
        api.getTour(KAKAO_KEY, category_code, lon, lat, 20000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<TourSearch>() {
                    @Override
                    public void onSuccess(@NonNull TourSearch tourSearch) {
                        if(tourSearch.getDocuments().size()==0){
                            Toast.makeText(context, "차박지 주변 관광지가 없어요", Toast.LENGTH_SHORT).show();
                        }else{
                            setData(tourSearch.getDocuments());
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        System.out.println(e);
                    }
                });
    }
    public void search_culture(){
        Gson gson=new GsonBuilder().setLenient().create();
        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://dapi.kakao.com/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        ThemeInterface api=retrofit.create(ThemeInterface.class);
        api.getCulture(KAKAO_KEY, category_code, lon, lat, 20000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<TourSearch>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull TourSearch tourSearch) {
                        if(tourSearch.getDocuments().size()==0){
                            Toast.makeText(context, "차박지 주변 문화지가 없어요", Toast.LENGTH_SHORT).show();
                        }else{
                            setData(tourSearch.getDocuments());
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }
    public void search_restaurant(){
        Gson gson=new GsonBuilder().setLenient().create();
        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://dapi.kakao.com/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        ThemeInterface api=retrofit.create(ThemeInterface.class);
        api.getRestaurant(KAKAO_KEY, category_code, lon, lat, 20000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<TourSearch>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull TourSearch tourSearch) {
                        if(tourSearch.getDocuments().size()==0){
                            Toast.makeText(context, "차박 주변 음식점이 없어요", Toast.LENGTH_SHORT).show();
                        }else{
                            setData(tourSearch.getDocuments());
                        }
                    }
                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }
    public void setData(List data){
        Gson gson=new Gson();
        String total=gson.toJson(data);
        Type type=new TypeToken<ArrayList<TourSearch.Place>>(){}.getType();
        list=gson.fromJson(total, type);
        Collections.sort(list);
        recommendAdapter.updateItem(list);
    }
}
