package com.tourkakao.carping.EcoCarping.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.Home.EcoDataClass.EcoReview;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EcoTotalViewModel extends ViewModel {
    private Context context;
    private MutableLiveData<ArrayList<EcoReview>> recentOrderReviews=new MutableLiveData<>();
    private MutableLiveData<ArrayList<EcoReview>> popularOrderReviews=new MutableLiveData<>();

    public void setContext(Context context){
        this.context=context;
    }

    public MutableLiveData<ArrayList<EcoReview>> getRecentOrderReviews(){return recentOrderReviews;}
    public MutableLiveData<ArrayList<EcoReview>> getPopularOrderReviews(){return popularOrderReviews;}

    public void setReviewData(String sort,ArrayList data){
        Gson gson=new Gson();
        if(sort.equals("recent")){
            data.remove(0);
        }
        String totalDataString=gson.toJson(data);
        ArrayList<EcoReview> reviews=gson.fromJson(totalDataString,new TypeToken<ArrayList<EcoReview>>(){}.getType());
        if(sort.equals("recent")){
            recentOrderReviews.setValue(reviews);
        }
        if(sort.equals("popular")){
            popularOrderReviews.setValue(reviews);
        }
    }

    public void loadRecentReviews(){
        TotalApiClient.getEcoApiService(context).getRecentEcoCarpingReview("recent",0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        setReviewData("recent",commonClass.getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void loadPopularReviews(){
        TotalApiClient.getEcoApiService(context).getPopularEcoCarpingReview("popular")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        setReviewData("popular",commonClass.getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
