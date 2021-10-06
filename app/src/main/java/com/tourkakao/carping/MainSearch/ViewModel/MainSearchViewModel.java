package com.tourkakao.carping.MainSearch.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.MainSearch.Adapter.MainSearchAdapter;
import com.tourkakao.carping.MainSearch.Adapter.RecentAdapter;
import com.tourkakao.carping.MainSearch.DataClass.MainSearch;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainSearchViewModel extends ViewModel {
    Context context;
    public MutableLiveData<ArrayList<String>> populars=new MutableLiveData<>();
    ArrayList<String> recents;
    RecentAdapter recentAdapter;
    public ArrayList<MainSearch> searches;
    MainSearchAdapter mainSearchAdapter;
    public MainSearchViewModel(){}

    public void setContext(Context context) {
        this.context = context;
    }
    public RecentAdapter setting_recentadapter(){
        recents=new ArrayList<>();
        recentAdapter=new RecentAdapter(context, recents);
        return recentAdapter;
    }
    public MainSearchAdapter setting_mainadapter(){
        searches=new ArrayList<>();
        mainSearchAdapter=new MainSearchAdapter(context, searches);
        return mainSearchAdapter;
    }
    public void get_popular_and_recent(){
        TotalApiClient.getApiService(context).get_popular_recent_keyword("main")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        res -> {
                            if(res.isSuccess()){
                                String result=new Gson().toJson(res.getData());
                                JSONArray total=new JSONArray(result);
                                JSONObject popularobject=(JSONObject) total.get(1);
                                JSONObject recentobject= (JSONObject) total.get(0);
                                JSONArray populararray=popularobject.getJSONArray("popular");
                                JSONArray recentarray=recentobject.getJSONArray("recent");
                                recents.clear();
                                for(int i=0; i<recentarray.length(); i++){
                                    recents.add(recentarray.get(i).toString());
                                }
                                recentAdapter.updateItem(recents);
                                ArrayList<String> pr=new ArrayList<>();
                                for(int i=0; i<populararray.length(); i++){
                                    pr.add(populararray.get(i).toString());
                                }
                                populars.setValue(pr);
                            }
                        },
                        error -> {

                        }
                );
    }
    public void searching(String keyword, double lat, double lon) {
        TotalApiClient.getApiService(context).main_search(keyword, lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        res -> {
                            if (res.isSuccess()) {
                                Type type = new TypeToken<ArrayList<MainSearch>>() {
                                }.getType();
                                String result = new Gson().toJson(res.getData(), type);
                                searches=new Gson().fromJson(result, type);
                                mainSearchAdapter.updateItem(searches);
                            }
                        },
                        error -> {

                        }
                );
    }
    public void search_complete(String keyword, String name){
        TotalApiClient.getApiService(context).save_search_keyword(keyword, name, "main")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        res -> {
                            if(res.isSuccess()){

                            }
                        },
                        error -> {

                        }
                );
    }
}
