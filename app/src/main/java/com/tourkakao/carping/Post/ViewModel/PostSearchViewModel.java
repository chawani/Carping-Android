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
import com.tourkakao.carping.Post.DTO.PostInfoDetail;
import com.tourkakao.carping.Post.DTO.PostListItem;
import com.tourkakao.carping.Post.DTO.PriceInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PostSearchViewModel extends ViewModel {
    private Context context;
    private MutableLiveData<ArrayList<PostListItem>> searchList=new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> recentList=new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> popularList=new MutableLiveData<>();
    private MutableLiveData<PriceInfo> priceInfoMutableLiveData=new MutableLiveData<>();
    private Gson gson=new Gson();

    public void setContext(Context context){
        this.context=context;
    }
    public MutableLiveData<ArrayList<PostListItem>> getSearchList(){
        return searchList;
    }
    public MutableLiveData<ArrayList<String>> getRecentList(){
        return recentList;
    }
    public MutableLiveData<ArrayList<String>> getPopularList(){
        return popularList;
    }
    public MutableLiveData<PriceInfo> getPriceInfoMutableLiveData() {return priceInfoMutableLiveData;}

    public void setPostListData(List data){
        String total=gson.toJson(data);
        ArrayList<PostListItem> listItems=gson.fromJson(total, new TypeToken<ArrayList<PostListItem>>(){}.getType());
        searchList.setValue(listItems);
    }

    public void setKeywordData(List data){
        JsonParser jsonParse = new JsonParser();
        String recentTotal=gson.toJson(data.get(0));
        String popularTotal=gson.toJson(data.get(1));
        JsonObject jsonObj = (JsonObject) jsonParse.parse(recentTotal);
        JsonArray array = (JsonArray) jsonObj.get("recent");
        recentTotal=gson.toJson(array);
        jsonObj = (JsonObject) jsonParse.parse(popularTotal);
        array = (JsonArray) jsonObj.get("popular");
        popularTotal=gson.toJson(array);
        ArrayList<String> stringArray=gson.fromJson(recentTotal,new TypeToken<ArrayList<String>>(){}.getType());
        recentList.setValue(stringArray);
        stringArray=gson.fromJson(popularTotal,new TypeToken<ArrayList<String>>(){}.getType());
        popularList.setValue(stringArray);
        System.out.println("최근 검색어"+gson.toJson(data));
    }

    public void setPriceData(List data,int point){
        String trade_fee_string=gson.toJson(data.get(0)).replace("{","").replace("}","");
        String platform_fee_string=gson.toJson(data.get(1)).replace("{","").replace("}","");
        String withholding_tax_string=gson.toJson(data.get(2)).replace("{","").replace("}","");
        String vat_string=gson.toJson(data.get(3)).replace("{","").replace("}","");
        String final_point_string=gson.toJson(data.get(4)).replace("{","").replace("}","");
        String total="{\"price\":"+Integer.toString(point)+","+trade_fee_string+","+platform_fee_string+","
                +withholding_tax_string+","+vat_string+","+final_point_string+"}";
        PriceInfo priceInfo=gson.fromJson(total,new TypeToken<PriceInfo>(){}.getType());
        priceInfoMutableLiveData.setValue(priceInfo);
    }

    public void searchInfo(String keyword){
        HashMap<String,Object> map=new HashMap<>();
        map.put("keyword",keyword);
        TotalApiClient.getPostApiService(context).search(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            setPostListData(commonClass.getData());
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

    public void completeSearch(String keyword,String name){
        HashMap<String,Object> map=new HashMap<>();
        map.put("keyword",keyword);
        map.put("name",name);
        map.put("type","post");
        TotalApiClient.getPostApiService(context).completeSearch(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {

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

    public void loadKeyword(){
        HashMap<String,Object> map=new HashMap<>();
        map.put("type","post");
        TotalApiClient.getPostApiService(context).getKeywordList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            setKeywordData(commonClass.getData());
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

    public void loadCalcResult(int point){
        HashMap<String,Object> map=new HashMap<>();
        map.put("point",point);
        TotalApiClient.getPostApiService(context).getCalcResult(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            setPriceData(commonClass.getData(),point);
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
}
