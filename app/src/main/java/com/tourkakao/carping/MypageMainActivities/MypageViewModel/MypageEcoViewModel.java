package com.tourkakao.carping.MypageMainActivities.MypageViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.Home.EcoDataClass.EcoReview;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MypageEcoViewModel extends ViewModel {
    private Gson gson=new Gson();
    private Context context;
    private MutableLiveData<ArrayList<EcoReview>> myReviews=new MutableLiveData<>();
    private MutableLiveData<ArrayList<EcoReview>> likeReivews=new MutableLiveData<>();

    public void setContext(Context context){
        this.context=context;
    }
    public MutableLiveData<ArrayList<EcoReview>> getMyReviews(){return myReviews;}
    public MutableLiveData<ArrayList<EcoReview>> getLikeReviews(){return likeReivews;}

    public void setMyPostData(ArrayList datas){
        String totalDataString=gson.toJson(datas);
        ArrayList<EcoReview> reviews=gson.fromJson(totalDataString,new TypeToken<ArrayList<EcoReview>>(){}.getType());
        myReviews.setValue(reviews);
    }

    public void setLikePostData(ArrayList datas){
        System.out.println("길이"+datas.size());
        String totalDataString=gson.toJson(datas);
        ArrayList<EcoReview> reviews=gson.fromJson(totalDataString,new TypeToken<ArrayList<EcoReview>>(){}.getType());
        likeReivews.setValue(reviews);
    }

    public void getEcoPosts(String subsort){
        TotalApiClient.getMypageApiService(context).getMypageActivities("eco",subsort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            if(subsort.equals("my"))
                                setMyPostData(commonClass.getData());
                            if(subsort.equals("like"))
                                setLikePostData(commonClass.getData());
                        }
                        else {
                            System.out.println("요청실패:"+commonClass.getCode()+commonClass.getError_message());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
