package com.tourkakao.carping.registernewcarping.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.Home.ThemeDataClass.NewCapringPlace;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.newcarping.DataClass.NewCarping;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchViewmodel extends ViewModel {
    private Context context;
    public MutableLiveData<Integer> isduplicate=new MutableLiveData<>();
    ArrayList<NewCapringPlace> newCapringPlaces=null;
    ArrayList<Double> lats=null;
    ArrayList<Double> lons=null;

    public SearchViewmodel(){
        isduplicate.setValue(-1);
        lats=new ArrayList<>();
        lons=new ArrayList<>();
    }
    public void setContext(Context context){
        this.context=context;
    }

    public void getting_registered_carping_place(){
        TotalApiClient.getApiService(context).get_newcarping_place(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lists-> {
                            Type type = new TypeToken<ArrayList<NewCapringPlace>>() {
                            }.getType();
                            String result = new Gson().toJson(lists.getData());
                            newCapringPlaces = new Gson().fromJson(result, type);
                            for(int i=0; i<newCapringPlaces.size(); i++){
                                int pk=newCapringPlaces.get(i).getPk();
                                TotalApiClient.getApiService(context).get_each_newcarping_place_detail(pk)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                                detail -> {
                                                    if(detail.getData()!=null){
                                                        Type type2=new TypeToken<NewCarping>(){}.getType();
                                                        String result2=new Gson().toJson(detail.getData().get(0));
                                                        NewCarping newCarping=new Gson().fromJson(result2, type2);

                                                        lats.add(Math.round(newCarping.getLatitude()*100000)/100000.0);
                                                        lons.add(Math.round(newCarping.getLongitude()*100000)/100000.0);
                                                    }
                                                },
                                                error -> {

                                                }
                                        );
                            }
                        },
                        error -> {

                        }
                );
    }

    public void check_isduplicate(double nowlat, double nowlon){
        isduplicate.setValue(-1);
        for(int i=0; i<lats.size(); i++){
            if(nowlat==lats.get(i) && nowlon==lons.get(i)){
                isduplicate.setValue(1);
                break;
            }
        }
        if(isduplicate.getValue()==-1){
            isduplicate.setValue(0);
        }
    }
}
