package com.tourkakao.carping.newcarping.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EachNewCarpingViewModel extends ViewModel {
    public MutableLiveData<String> title=new MutableLiveData<>();
    public MutableLiveData<String> total_star_num=new MutableLiveData<>();
    public MutableLiveData<String> address=new MutableLiveData<>();
    public MutableLiveData<Float> total_star=new MutableLiveData<>();
    public MutableLiveData<String> info_review=new MutableLiveData<>();
    public MutableLiveData<String> info_tags=new MutableLiveData<>();
    public MutableLiveData<Float> review_user_star=new MutableLiveData<>();
    public MutableLiveData<String> review_username=new MutableLiveData<>();
    public MutableLiveData<Integer> review_cnt=new MutableLiveData<>();
    public MutableLiveData<String> review_count=new MutableLiveData<>();
    public MutableLiveData<Float> review_star_num=new MutableLiveData<>();
    public MutableLiveData<Float> review_star=new MutableLiveData<>();
    public MutableLiveData<Float> review_star1=new MutableLiveData<>();
    public MutableLiveData<Float> review_star2=new MutableLiveData<>();
    public MutableLiveData<Float> review_star3=new MutableLiveData<>();
    public MutableLiveData<Float> review_star4=new MutableLiveData<>();
    public MutableLiveData<Float> review_star1_num=new MutableLiveData<>();
    public MutableLiveData<Float> review_star2_num=new MutableLiveData<>();
    public MutableLiveData<Float> review_star3_num=new MutableLiveData<>();
    public MutableLiveData<Float> review_star4_num=new MutableLiveData<>();
    private Context context;

    public EachNewCarpingViewModel(){
        review_cnt.setValue(-1);
    }
    public void setContext(Context context){
        this.context=context;
    }
}
