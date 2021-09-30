package com.tourkakao.carping.Home.SearchViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.Home.SearchAdapter.SearchAdapter;
import com.tourkakao.carping.Home.ThemeDataClass.Search;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchViewModel extends ViewModel {
    Context context;
    public ArrayList<Search> populars;
    SearchAdapter adapter;
    public MutableLiveData<Integer> change_popular=new MutableLiveData<>();
    public SearchViewModel(){
        change_popular.setValue(0);
    }
    public void setContext(Context context) {
        this.context = context;
    }

    public SearchAdapter setting_adapter(){
        populars=new ArrayList<>();
        adapter=new SearchAdapter(context, populars);
        return adapter;
    }

    public void get_popularlist(String region){
        TotalApiClient.getApiService(context).get_eachcity(region)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        res -> {
                            if(res.isSuccess()){
                                Type type=new TypeToken<ArrayList<Search>>(){}.getType();
                                String result=new Gson().toJson(res.getData());
                                populars=new Gson().fromJson(result, type);
                                if(populars.size()==0){
                                    populars.add(new Search(-1, "인기 검색어가 없어요"));
                                }
                                adapter.updateItem(populars);
                                change_popular.setValue(1);
                                change_popular.setValue(0);
                            }
                        },
                        error -> {

                        }
                );
    }
}
