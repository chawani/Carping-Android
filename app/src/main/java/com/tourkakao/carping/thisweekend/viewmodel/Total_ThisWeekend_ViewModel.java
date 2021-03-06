package com.tourkakao.carping.thisweekend.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.Home.ThemeDataClass.Thisweekend;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.thisweekend.Activity.Each_ThisWeekendActivity;
import com.tourkakao.carping.thisweekend.Activity.ThisWeekend_totalActivity;
import com.tourkakao.carping.thisweekend.Adapter.Total_ThisWeekend_Adapter;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Total_ThisWeekend_ViewModel extends ViewModel {
    private Context context;

    public MutableLiveData<Integer> total_thisweekends_post_cnt=new MutableLiveData<>();
    Total_ThisWeekend_Adapter total_thisWeekend_adapter;
    ArrayList<Thisweekend> thisweekends=null;

    public Total_ThisWeekend_ViewModel(){
        total_thisweekends_post_cnt.setValue(-1);
    }
    public void setContext(Context context){
        this.context=context;
    }
    public Total_ThisWeekend_Adapter setting_total_thisweekend_adpater(){
        thisweekends=new ArrayList<>();
        total_thisWeekend_adapter=new Total_ThisWeekend_Adapter(context, thisweekends);
        total_thisWeekend_adapter.setOnSelectItemClickListener(new Total_ThisWeekend_Adapter.OnSelectItemClickListener() {
            @Override
            public void OnSelectItemClick(View v, int pos, int pk) {
                Intent intent=new Intent(context, Each_ThisWeekendActivity.class);
                intent.putExtra("pk", pk);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return total_thisWeekend_adapter;
    }
    public void getTotal_thisweekends(){
        TotalApiClient.getApiService(context).get_thisweekend_post(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lists -> {
                            if(lists.isSuccess()) {
                                total_thisweekends_post_cnt.setValue(lists.getData().size());
                                Type type = new TypeToken<ArrayList<Thisweekend>>() {
                                }.getType();
                                String result = new Gson().toJson(lists.getData());
                                thisweekends = new Gson().fromJson(result, type);
                                total_thisWeekend_adapter.update_Item(thisweekends);
                            }else{
                                System.out.println(lists.getError_message());
                            }
                        },
                        error -> {}
                );
    }
}
