package com.tourkakao.carping.thisweekend.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.thisweekend.DataClass.Each_campsite;
import com.tourkakao.carping.thisweekend.DataClass.Totalthisweekend;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Each_ThisWeekend_ViewModel extends ViewModel {
    private Context context;
    private int pk;
    public MutableLiveData<String> thumbnail=new MutableLiveData<>();
    public MutableLiveData<Integer> count=new MutableLiveData<>();
    public MutableLiveData<String> title=new MutableLiveData<>();
    public MutableLiveData<String> tags=new MutableLiveData<>();
    public MutableLiveData<String> name1=new MutableLiveData<>();
    public MutableLiveData<String> image1=new MutableLiveData<>();
    public MutableLiveData<String> source1=new MutableLiveData<>();
    public MutableLiveData<String> title1=new MutableLiveData<>();
    public MutableLiveData<String> body1=new MutableLiveData<>();
    public MutableLiveData<String> address1=new MutableLiveData<>();
    public MutableLiveData<String> phone1=new MutableLiveData<>();
    public MutableLiveData<String> type1=new MutableLiveData<>();
    public MutableLiveData<String> oper1=new MutableLiveData<>();
    public MutableLiveData<String> website1=new MutableLiveData<>();
    public MutableLiveData<String> facility1=new MutableLiveData<>();
    public MutableLiveData<String> name2=new MutableLiveData<>();
    public MutableLiveData<String> image2=new MutableLiveData<>();
    public MutableLiveData<String> source2=new MutableLiveData<>();
    public MutableLiveData<String> title2=new MutableLiveData<>();
    public MutableLiveData<String> body2=new MutableLiveData<>();
    public MutableLiveData<String> address2=new MutableLiveData<>();
    public MutableLiveData<String> phone2=new MutableLiveData<>();
    public MutableLiveData<String> type2=new MutableLiveData<>();
    public MutableLiveData<String> oper2=new MutableLiveData<>();
    public MutableLiveData<String> website2=new MutableLiveData<>();
    public MutableLiveData<String> facility2=new MutableLiveData<>();
    public MutableLiveData<String> name3=new MutableLiveData<>();
    public MutableLiveData<String> image3=new MutableLiveData<>();
    public MutableLiveData<String> source3=new MutableLiveData<>();
    public MutableLiveData<String> title3=new MutableLiveData<>();
    public MutableLiveData<String> body3=new MutableLiveData<>();
    public MutableLiveData<String> address3=new MutableLiveData<>();
    public MutableLiveData<String> phone3=new MutableLiveData<>();
    public MutableLiveData<String> type3=new MutableLiveData<>();
    public MutableLiveData<String> oper3=new MutableLiveData<>();
    public MutableLiveData<String> website3=new MutableLiveData<>();
    public MutableLiveData<String> facility3=new MutableLiveData<>();

    public Each_ThisWeekend_ViewModel(){
        count.setValue(0);
    }
    public void setContext(Context context){
        this.context=context;
    }
    public void setPk(int pk){
        this.pk=pk;
    }
    public void get_each_thisweekend(){
        TotalApiClient.getApiService(context).get_each_thisweekend_detail(pk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        detail -> {
                            if(detail.isSuccess()) {
                                Type type = new TypeToken<Each_campsite>() {
                                }.getType();
                                String result = new Gson().toJson(detail.getData());
                                JSONArray array = new JSONArray(result);
                                JSONObject object = array.getJSONObject(0);
                                count.setValue(object.getInt("count"));
                                title.setValue(object.getString("title"));
                                thumbnail.setValue(object.getString("thumbnail"));
                                JSONArray t=object.getJSONArray("tags");
                                System.out.println(t);
                                String tmp="";
                                for(int i=0; i<t.length(); i++){
                                    tmp+="#"+t.get(i).toString()+" ";
                                }
                                tags.setValue(tmp);
                                Each_campsite campsite1 = new Gson().fromJson(String.valueOf(object.getJSONObject("campsite1")), type);
                                Each_campsite campsite2 = new Gson().fromJson(String.valueOf(object.getJSONObject("campsite2")), type);
                                Each_campsite campsite3 = new Gson().fromJson(String.valueOf(object.getJSONObject("campsite3")), type);
                                name1.setValue(campsite1.getCampsite_name());
                                image1.setValue(campsite1.getImage());
                                source1.setValue("사진출처 "+campsite1.getImage_source());
                                title1.setValue(campsite1.getTitle());
                                body1.setValue(campsite1.getBody());
                                if(campsite1.getAddress()==null){
                                    address1.setValue("정보 없음");
                                }else {
                                    address1.setValue(campsite1.getAddress());
                                }
                                if(campsite1.getPhone()==null){
                                    phone1.setValue("정보 없음");
                                }else {
                                    phone1.setValue(campsite1.getPhone());
                                }
                                if(campsite1.getType()==null){
                                    type1.setValue("정보 없음");
                                }else {
                                    type1.setValue(campsite1.getType());
                                }
                                if(campsite1.getOper_day()==null){
                                    oper1.setValue("정보 없음");
                                }else {
                                    oper1.setValue(campsite1.getOper_day());
                                }
                                if(campsite1.getWebsite()==null){
                                    website1.setValue("정보 없음");
                                }else {
                                    website1.setValue(campsite1.getWebsite());
                                }
                                if(campsite1.getSub_facility()==null){
                                    facility1.setValue("정보 없음");
                                }else {
                                    facility1.setValue(campsite1.getSub_facility());
                                }
                                name2.setValue(campsite2.getCampsite_name());
                                image2.setValue(campsite2.getImage());
                                source2.setValue("사진출처 "+campsite2.getImage_source());
                                title2.setValue(campsite2.getTitle());
                                body2.setValue(campsite2.getBody());
                                if(campsite2.getAddress()==null){
                                    address2.setValue("정보 없음");
                                }else {
                                    address2.setValue(campsite2.getAddress());
                                }
                                if(campsite2.getPhone()==null){
                                    phone2.setValue("정보 없음");
                                }else {
                                    phone2.setValue(campsite2.getPhone());
                                }
                                if(campsite2.getType()==null){
                                    type2.setValue("정보 없음");
                                }else {
                                    type2.setValue(campsite2.getType());
                                }
                                if(campsite2.getOper_day()==null){
                                    oper2.setValue("정보 없음");
                                }else {
                                    oper2.setValue(campsite2.getOper_day());
                                }
                                if(campsite2.getWebsite()==null){
                                    website2.setValue("정보 없음");
                                }else {
                                    website2.setValue(campsite2.getWebsite());
                                }
                                if(campsite2.getSub_facility()==null){
                                    facility2.setValue("정보 없음");
                                }else {
                                    facility2.setValue(campsite2.getSub_facility());
                                }
                                name3.setValue(campsite3.getCampsite_name());
                                image3.setValue(campsite3.getImage());
                                source3.setValue("사진출처 "+campsite3.getImage_source());
                                title3.setValue(campsite3.getTitle());
                                body3.setValue(campsite3.getBody());
                                if(campsite3.getAddress()==null){
                                    address3.setValue("정보 없음");
                                }else {
                                    address3.setValue(campsite3.getAddress());
                                }
                                if(campsite3.getPhone()==null){
                                    phone3.setValue("정보 없음");
                                }else {
                                    phone3.setValue(campsite3.getPhone());
                                }
                                if(campsite3.getType()==null){
                                    type3.setValue("정보 없음");
                                }else {
                                    type3.setValue(campsite3.getType());
                                }
                                if(campsite3.getOper_day()==null){
                                    oper3.setValue("정보 없음");
                                }else {
                                    oper3.setValue(campsite3.getOper_day());
                                }
                                if(campsite3.getWebsite()==null){
                                    website3.setValue("정보 없음");
                                }else {
                                    website3.setValue(campsite3.getWebsite());
                                }
                                if(campsite3.getSub_facility()==null){
                                    facility3.setValue("정보 없음");
                                }else {
                                    facility3.setValue(campsite3.getSub_facility());
                                }
                            }else{
                                System.out.println(detail.getError_message());
                            }
                        },
                        error -> {
                            System.out.println(error);
                        }
                );
    }
}
