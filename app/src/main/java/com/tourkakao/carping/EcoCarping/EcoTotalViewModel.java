package com.tourkakao.carping.EcoCarping;

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
    private MutableLiveData<ArrayList<EcoReview>> distanceOrderReviews=new MutableLiveData<>();
    private Float latitude;
    private Float longitude;

    public EcoTotalViewModel(){
        updateRecentReviews();
        updatePopularReviews();
    }

    public void startDistance(){
        updateDistanceReviews();
    }

    public void setContext(Context context){
        this.context=context;
    }

    public void setLocation(Float latitude,Float longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public MutableLiveData<ArrayList<EcoReview>> getRecentOrderReviews(){return recentOrderReviews;}
    public MutableLiveData<ArrayList<EcoReview>> getPopularOrderReviews(){return popularOrderReviews;}
    public MutableLiveData<ArrayList<EcoReview>> getDistanceOrderReviews(){return distanceOrderReviews;}

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
        if(sort.equals("distance")){
            distanceOrderReviews.setValue(reviews);
        }
    }

    public void updateRecentReviews(){
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

    public void updatePopularReviews(){
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

    public void updateDistanceReviews(){
        TotalApiClient.getEcoApiService(context).getDistanceEcoCarpingReview("distance",latitude,longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        setReviewData("distance",commonClass.getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
