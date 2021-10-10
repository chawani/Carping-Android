package com.tourkakao.carping.MypageMainActivities.MypageViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.GpsLocation.GpsTracker;
import com.tourkakao.carping.MypageMainActivities.DTO.Autocamp;
import com.tourkakao.carping.MypageMainActivities.DTO.Campsite;
import com.tourkakao.carping.MypageMainActivities.DTO.MyCarpingPost;
import com.tourkakao.carping.MypageMainActivities.DTO.ScrapData;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MypageCarpingViewModel extends ViewModel {
    private Gson gson=new Gson();
    private Context context;
    private MutableLiveData<ArrayList<MyCarpingPost>> myCarpings=new MutableLiveData<>();
    private MutableLiveData<ArrayList<Campsite>> campsitesLiveData=new MutableLiveData<>();
    private MutableLiveData<ArrayList<MyCarpingPost>> autocampsLiveData=new MutableLiveData<>();
    private MutableLiveData<Integer> postSize=new MutableLiveData<>();

    public void setContext(Context context){
        this.context=context;
    }
    public MutableLiveData<ArrayList<MyCarpingPost>> getMyCarpings(){return myCarpings;}
    public MutableLiveData<ArrayList<Campsite>> getCampsitesLiveData(){return campsitesLiveData;}
    public MutableLiveData<ArrayList<MyCarpingPost>> getAutocampsLiveData(){return autocampsLiveData;}
    public MutableLiveData<Integer> getPostSize(){return postSize;}

    public void setMyPostData(ArrayList data) {
        String totalString=gson.toJson(data);
        ArrayList<MyCarpingPost> posts=gson.fromJson(totalString, new TypeToken<ArrayList<MyCarpingPost>>(){}.getType());
        myCarpings.setValue(posts);
    }

    public void setScarpPostData(ArrayList data) {
        postSize.setValue(0);
        String total=gson.toJson(data);System.out.println("스끄랩"+total);
        ScrapData campsiteData=gson.fromJson(gson.toJson(data.get(0)),ScrapData.class);
        ArrayList<Campsite> campsites=campsiteData.getCampsite();
        ScrapData autocampsData=gson.fromJson(gson.toJson(data.get(1)),ScrapData.class);
        ArrayList<MyCarpingPost> autocamps=autocampsData.getAutocamp();

        campsitesLiveData.setValue(campsites);
        autocampsLiveData.setValue(autocamps);
        if(campsites!=null){
            postSize.setValue(postSize.getValue()+campsites.size());
        }
        if(autocamps!=null) {
            postSize.setValue(postSize.getValue()+autocamps.size());
        }
    }

    public void loadMyCarpings(){
        TotalApiClient.getMypageApiService(context).getMypageActivities("autocamp","my")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                                setMyPostData(commonClass.getData());
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

    public void loadScrapCarpings(){
        GpsTracker gpsTracker = new GpsTracker(context);
        String lat = Double.toString(gpsTracker.getLatitude());
        String lon = Double.toString(gpsTracker.getLongitude());
        TotalApiClient.getMypageApiService(context).getScrap("autocamp","scrap",lat,lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            setScarpPostData(commonClass.getData());
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
