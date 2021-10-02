package com.tourkakao.carping.Post.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.Home.EcoDataClass.EcoReview;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.Post.DTO.Review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PostReviewViewModel extends ViewModel {
    private Context context;
    private Gson gson=new Gson();
    private MutableLiveData<ArrayList<Review>> recentOrderReviews=new MutableLiveData<>();
    private MutableLiveData<ArrayList<Review>> popularOrderReviews=new MutableLiveData<>();

    public void setContext(Context context){
        this.context=context;
    }

    public MutableLiveData<ArrayList<Review>> getRecentOrderReviews(){return recentOrderReviews;}
    public MutableLiveData<ArrayList<Review>> getPopularOrderReviews(){return popularOrderReviews;}

    public void setReviewsData(String sort,List data){
        String totalDataString=gson.toJson(data);
        ArrayList<Review> reviews=gson.fromJson(totalDataString,new TypeToken<ArrayList<Review>>(){}.getType());
        if(sort.equals("recent")){
            recentOrderReviews.setValue(reviews);
        }
        if(sort.equals("popular")){
            popularOrderReviews.setValue(reviews);
        }
    }

    public void postReview(HashMap<String,Object> map){
        TotalApiClient.getPostApiService(context).postReview(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            System.out.println("리뷰 완료"+commonClass.getData().get(0).toString());
                        }
                        else {
                            System.out.println("요청실패:"+commonClass.getCode()+commonClass.getError_message());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("에러"+e.getMessage());
                    }
                });
    }

    public void deleteComment(int pk){
        TotalApiClient.getPostApiService(context).deleteReview(pk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void loadReviews(String pk,String sort){
        HashMap<String,String> map=new HashMap<>();
        map.put("sort",sort);
        TotalApiClient.getPostApiService(context).getReviewTotal(Integer.parseInt(pk),map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            setReviewsData(sort,commonClass.getData());
                        }
                        else {
                            System.out.println("요청실패:"+commonClass.getCode()+commonClass.getError_message());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("에러"+e.getMessage());
                    }
                });
    }

    public void pushLike(int pk){
        HashMap<String,Object> map=new HashMap<>();
        map.put("review_to_like",pk);
        TotalApiClient.getPostApiService(context).likeReview(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            System.out.println(gson.toJson(commonClass.getData()));
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

    public void cancelLike(int pk){
        HashMap<String,Object> map=new HashMap<>();
        map.put("review_to_like",pk);
        TotalApiClient.getPostApiService(context).cancelReviewLike(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            System.out.println(gson.toJson(commonClass.getData()));
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
