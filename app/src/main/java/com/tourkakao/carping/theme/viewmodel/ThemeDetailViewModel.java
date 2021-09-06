package com.tourkakao.carping.theme.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ThemeDetailViewModel extends ViewModel {
    private Context context;
    public MutableLiveData<String> name=new MutableLiveData<>();
    public MutableLiveData<String> address=new MutableLiveData<>();
    public MutableLiveData<String> day=new MutableLiveData<>();
    public MutableLiveData<String> weekend=new MutableLiveData<>();
    public MutableLiveData<String> season=new MutableLiveData<>();
    public MutableLiveData<String> phone=new MutableLiveData<>();
    public MutableLiveData<String> website=new MutableLiveData<>();
    public MutableLiveData<String> oper_info=new MutableLiveData<>();
    public MutableLiveData<String> major=new MutableLiveData<>();
    public MutableLiveData<String> sub=new MutableLiveData<>();
    public MutableLiveData<String> reserve=new MutableLiveData<>();
    public MutableLiveData<String> pet=new MutableLiveData<>();
    public MutableLiveData<String> fire=new MutableLiveData<>();

    public ThemeDetailViewModel(){}
    public void setContext(Context context){
        this.context=context;
    }
}
