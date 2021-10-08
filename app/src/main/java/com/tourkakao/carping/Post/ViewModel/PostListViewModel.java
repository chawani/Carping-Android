package com.tourkakao.carping.Post.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.EcoCarping.DTO.EcoPost;
import com.tourkakao.carping.Home.EcoDataClass.EcoReview;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.Post.DTO.PostListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PostListViewModel extends ViewModel {
    private Context context;
    private MutableLiveData<ArrayList<PostListItem>> popularLiveData=new MutableLiveData<>();
    private MutableLiveData<ArrayList<PostListItem>> beginnerLiveData=new MutableLiveData<>();
    private MutableLiveData<ArrayList<PostListItem>> allOfLiveData=new MutableLiveData<>();
    private MutableLiveData<ArrayList<PostListItem>> carLiveData=new MutableLiveData<>();
    private MutableLiveData<ArrayList<PostListItem>> categoryData=new MutableLiveData<>();

    public void setContext(Context context){
        this.context=context;
    }
    public MutableLiveData<ArrayList<PostListItem>> getPopularLiveData(){
        return popularLiveData;
    }
    public MutableLiveData<ArrayList<PostListItem>> getBeginnerLiveData(){
        return beginnerLiveData;
    }
    public MutableLiveData<ArrayList<PostListItem>> getAllOfLiveData(){
        return allOfLiveData;
    }
    public MutableLiveData<ArrayList<PostListItem>> getCarLiveData(){
        return carLiveData;
    }
    public MutableLiveData<ArrayList<PostListItem>> getCategoryData(){
        return categoryData;
    }

    public void setTotalData(List data){
        ArrayList<PostListItem> popular=new ArrayList<>();
        ArrayList<PostListItem> beginner=new ArrayList<>();
        ArrayList<PostListItem> allOf=new ArrayList<>();
        ArrayList<PostListItem> car=new ArrayList<>();

        Gson gson=new Gson();
        String total=gson.toJson(data);
        ArrayList<PostListItem> listItems=gson.fromJson(total, new TypeToken<ArrayList<PostListItem>>(){}.getType());
        for(PostListItem item:listItems){
            int category=item.getCategory();
            if(category==4){
                popular.add(item);
            }
            if(category==1){
                beginner.add(item);
            }
            if(category==2){
                allOf.add(item);
            }
            if(category==3){
                car.add(item);
            }
        }
        popularLiveData.setValue(popular);
        beginnerLiveData.setValue(beginner);
        allOfLiveData.setValue(allOf);
        carLiveData.setValue(car);
    }

    public void setCategoryData(List data){
        Gson gson=new Gson();
        String total=gson.toJson(data);
        ArrayList<PostListItem> listItems=gson.fromJson(total, new TypeToken<ArrayList<PostListItem>>(){}.getType());
        categoryData.setValue(listItems);
    }

    public void loadTotalList(){
        HashMap<String,Object> map=new HashMap<>();
        map.put("type",2);
        TotalApiClient.getPostApiService(context).getPostList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                                setTotalData(commonClass.getData());
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

    public void loadCategoryList(int category){
        HashMap<String,Object> map=new HashMap<>();
        map.put("type",3);
        map.put("category",category);
        TotalApiClient.getPostApiService(context).getPostList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            setCategoryData(commonClass.getData());
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

    public void postLike(int pk){
        HashMap<String,Object> map=new HashMap<>();
        map.put("post_to_like",pk);
        TotalApiClient.getPostApiService(context).postLike(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            System.out.println("좋아요 완료"+commonClass.getData().get(0).toString());
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

    public void cancelLike(int pk){
        HashMap<String,Object> map=new HashMap<>();
        map.put("post_to_like",pk);
        TotalApiClient.getPostApiService(context).cancelLike(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            System.out.println("좋아요 완료"+commonClass.getData().get(0).toString());
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
