package com.tourkakao.carping.theme.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.theme.Dataclass.DetailTheme;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThemeDetailViewModel extends ViewModel {
    private Context context;
    public MutableLiveData<String> image=new MutableLiveData<>();
    public MutableLiveData<String> name=new MutableLiveData<>();
    public MutableLiveData<String> address=new MutableLiveData<>();
    public MutableLiveData<String> day=new MutableLiveData<>();
    public MutableLiveData<String> season=new MutableLiveData<>();
    public MutableLiveData<String> phone=new MutableLiveData<>();
    public MutableLiveData<String> website=new MutableLiveData<>();
    public MutableLiveData<String> oper_info=new MutableLiveData<>();
    public MutableLiveData<String> major=new MutableLiveData<>();
    public MutableLiveData<String> sub=new MutableLiveData<>();
    public MutableLiveData<String> reserve=new MutableLiveData<>();
    public MutableLiveData<String> pet=new MutableLiveData<>();
    public MutableLiveData<String> fire=new MutableLiveData<>();
    public MutableLiveData<Float> carping_lat=new MutableLiveData<>();
    public MutableLiveData<Float> carping_lon=new MutableLiveData<>();

    public ThemeDetailViewModel(){}
    public void setContext(Context context){
        this.context=context;
    }

    public void getting_themedetail(int pk, double lat, double lon){
        TotalApiClient.getApiService(context).get_themedetail(pk, lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            if(result.isSuccess()){
                                Type type=new TypeToken<ArrayList<DetailTheme>>(){}.getType();
                                String res=new Gson().toJson(result.getData());
                                System.out.println(result.getData());
                                ArrayList<DetailTheme> d=new Gson().fromJson(res, type);
                                DetailTheme detailTheme=d.get(0);
                                image.setValue(detailTheme.getImage());
                                if(detailTheme.getName()==null){
                                    name.setValue("정보없음");
                                }else {
                                    name.setValue(detailTheme.getName());
                                }
                                if(detailTheme.getAddress()==null){
                                    address.setValue("정보없음");
                                }else {
                                    address.setValue(detailTheme.getAddress());
                                }
                                if(detailTheme.getOper_day()==null){
                                    day.setValue("정보없음");
                                }else{
                                    day.setValue(detailTheme.getOper_day());
                                }
                                if(detailTheme.getSeason()==null){
                                    season.setValue("정보없음");
                                }else{
                                    season.setValue(detailTheme.getSeason());
                                }
                                if(detailTheme.getPhone()==null){
                                    phone.setValue("정보없음");
                                }else{
                                    phone.setValue(detailTheme.getPhone());
                                }
                                if(detailTheme.getWebsite()==null){
                                    website.setValue("정보없음");
                                }else{
                                    website.setValue(detailTheme.getWebsite());
                                }
                                if(detailTheme.getPermission_date()==null){
                                    oper_info.setValue("정보없음");
                                }else{
                                    oper_info.setValue(detailTheme.getPermission_date());
                                }
                                if(detailTheme.getMain_facility()==null){
                                    major.setValue("정보없음");
                                }else{
                                    major.setValue(detailTheme.getMain_facility());
                                }
                                if(detailTheme.getSub_facility()==null){
                                    sub.setValue("정보없음");
                                }else{
                                    sub.setValue(detailTheme.getSub_facility());
                                }
                                if(detailTheme.getReservation()==null){
                                    reserve.setValue("정보없음");
                                }else{
                                    reserve.setValue(detailTheme.getReservation());
                                }
                                if(detailTheme.getAnimal()==null){
                                    pet.setValue("정보없음");
                                }else{
                                    pet.setValue(detailTheme.getAnimal());
                                }
                                if(detailTheme.getBrazier()==null){
                                    fire.setValue("정보없음");
                                }else{
                                    fire.setValue(detailTheme.getBrazier());
                                }
                                carping_lat.setValue(detailTheme.getLat());
                                carping_lon.setValue(detailTheme.getLon());
                            }else{
                                System.out.println(result.getError_message());
                            }
                        },
                        error -> {

                        }
                );
    }
}
