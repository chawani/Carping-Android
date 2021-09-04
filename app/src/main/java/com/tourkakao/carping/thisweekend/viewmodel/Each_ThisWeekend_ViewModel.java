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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Each_ThisWeekend_ViewModel extends ViewModel {
    private Context context;
    private int pk;
    public MutableLiveData<Integer> count=new MutableLiveData<>();
    public MutableLiveData<String> title=new MutableLiveData<>();
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
                                Each_campsite campsite1 = new Gson().fromJson(String.valueOf(object.getJSONObject("campsite1")), type);
                                Each_campsite campsite2 = new Gson().fromJson(String.valueOf(object.getJSONObject("campsite2")), type);
                                Each_campsite campsite3 = new Gson().fromJson(String.valueOf(object.getJSONObject("campsite3")), type);
                                name1.setValue(campsite1.getCampsite_name());
                                image1.setValue(campsite1.getImage());
                                source1.setValue(campsite1.getImage_source());
                                title1.setValue(campsite1.getTitle());
                                body1.setValue(campsite1.getBody());
                                address1.setValue(campsite1.getAddress());
                                phone1.setValue(campsite1.getPhone());
                                type1.setValue(campsite1.getType());
                                oper1.setValue(campsite1.getOper_day());
                                website1.setValue(campsite1.getWebsite());
                                facility1.setValue(campsite1.getSub_facility());
                                name2.setValue(campsite2.getCampsite_name());
                                image2.setValue(campsite2.getImage());
                                source2.setValue(campsite2.getImage_source());
                                title2.setValue(campsite2.getTitle());
                                body2.setValue(campsite2.getBody());
                                address2.setValue(campsite2.getAddress());
                                phone2.setValue(campsite2.getPhone());
                                type2.setValue(campsite2.getType());
                                oper2.setValue(campsite2.getOper_day());
                                website2.setValue(campsite2.getWebsite());
                                facility2.setValue(campsite2.getSub_facility());
                                name3.setValue(campsite3.getCampsite_name());
                                image3.setValue(campsite3.getImage());
                                source3.setValue(campsite3.getImage_source());
                                title3.setValue(campsite3.getTitle());
                                body3.setValue(campsite3.getBody());
                                address3.setValue(campsite3.getAddress());
                                phone3.setValue(campsite3.getPhone());
                                type3.setValue(campsite3.getType());
                                oper3.setValue(campsite3.getOper_day());
                                website3.setValue(campsite3.getWebsite());
                                facility3.setValue(campsite3.getSub_facility());
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
