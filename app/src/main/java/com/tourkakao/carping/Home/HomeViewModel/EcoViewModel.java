package com.tourkakao.carping.Home.HomeViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.Home.EcoDataClass.EcoRanking;
import com.tourkakao.carping.Home.EcoDataClass.EcoRankingData;
import com.tourkakao.carping.Home.EcoDataClass.EcoReview;
import com.tourkakao.carping.Home.EcoDataClass.MoreInfo;
import com.tourkakao.carping.Home.EcoFragmentAdapter.EcoRankingAdapter;
import com.tourkakao.carping.Home.EcoFragmentAdapter.EcoReviewAdapter;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EcoViewModel extends ViewModel {
    private Context context;
    private Gson gson=new Gson();
    public MutableLiveData<ArrayList<EcoReview>> ecoReviewLiveData=new MutableLiveData<>();
    public MutableLiveData<ArrayList<EcoRanking>> ecoRankingLiveData=new MutableLiveData<>();
    private ArrayList<EcoReview> ecoReviews;
    private ArrayList<EcoRanking> ecoRanking;
    public MutableLiveData<Integer> todayLiveCount=new MutableLiveData<>();
    public MutableLiveData<EcoRanking> currentUser=new MutableLiveData<>();
    public MutableLiveData<Integer> ecoPercentage=new MutableLiveData<>();
    public MutableLiveData<Integer> monthlyEcoCount=new MutableLiveData<>();

    public EcoViewModel(){
        getReviews();
        getRanking();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setReviewData(ArrayList data){
        String todayCountJsonString=gson.toJson(data.get(0));
        JsonElement element = new JsonParser().parse(todayCountJsonString);
        Integer todayCount = element.getAsJsonObject().get("today_count").getAsInt();
        todayLiveCount.setValue(todayCount);
        data.remove(0);
        Type reviewType=new TypeToken<ArrayList<EcoReview>>(){}.getType();
        String reviewListJsonString=gson.toJson(data);
        ecoReviews =gson.fromJson(reviewListJsonString,reviewType);
        ecoReviewLiveData.setValue(ecoReviews);
    }

    public void setRankingData(ArrayList data){
        String currentUserString=gson.toJson(data.get(0));
        EcoRanking user=gson.fromJson(currentUserString,EcoRanking.class);
        currentUser.setValue(user);
        String moreInfoJsonString=gson.toJson(data.get(1));
        MoreInfo moreInfo=gson.fromJson(moreInfoJsonString,MoreInfo.class);
        ecoPercentage.setValue(moreInfo.getEco_percentage());
        monthlyEcoCount.setValue(moreInfo.getMonthly_eco_count());
        String rankingData=gson.toJson(data.get(2));
        ecoRanking=gson.fromJson(rankingData,new TypeToken<ArrayList<EcoRanking>>(){}.getType());
        ecoRankingLiveData.setValue(ecoRanking);
    }

    public void getReviews(){
        TotalApiClient.getEcoApiService(context).getRecentEcoCarpingReview("recent",3)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                            @Override
                            public void onSuccess(@NonNull CommonClass commonClass) {
                                setReviewData(commonClass.getData());
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }
                        });
    }

    public void getRanking(){
        TotalApiClient.getEcoApiService(context).getEcoCarpingRanking()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        setRankingData(commonClass.getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
