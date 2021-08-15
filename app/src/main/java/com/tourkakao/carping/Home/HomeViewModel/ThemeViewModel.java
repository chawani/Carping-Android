package com.tourkakao.carping.Home.HomeViewModel;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.internal.service.Common;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.Home.Fragment.ThemeFragment;
import com.tourkakao.carping.Home.ThemeDataClass.AZPost;
import com.tourkakao.carping.Home.ThemeDataClass.Thisweekend;
import com.tourkakao.carping.Home.ThemeFragmentAdapter.Az_Adapter;
import com.tourkakao.carping.Home.ThemeFragmentAdapter.NewCarpingPlace_Adapter;
import com.tourkakao.carping.Home.ThemeFragmentAdapter.PopularCarpingPlace_Adapter;
import com.tourkakao.carping.Home.ThemeFragmentAdapter.ThisWeekend_Adapter;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThemeViewModel extends ViewModel {
    private Context context;
    //for thisweekends post in main
    public MutableLiveData<Integer> main_thisweekends_post_cnt=new MutableLiveData<>();
    ThisWeekend_Adapter thisWeekend_adapter;
    ArrayList<Thisweekend> thisweekends=null;
    //for new post in main
    public MutableLiveData<Integer> main_new_post_cnt=new MutableLiveData<>();
    NewCarpingPlace_Adapter newCarpingPlace_adapter;

    //for az post in main
    public MutableLiveData<Integer> main_az_post_cnt=new MutableLiveData<>();
    Az_Adapter az_adapter;
    ArrayList<AZPost> azPosts=null;

    //for popular carping in main
    public MutableLiveData<Integer> main_popular_cnt=new MutableLiveData<>();
    PopularCarpingPlace_Adapter popularCarpingPlace_adapter;

    public ThemeViewModel(){
        main_thisweekends_post_cnt.setValue(0);
        main_new_post_cnt.setValue(0);
        main_az_post_cnt.setValue(0);
        main_popular_cnt.setValue(0);
    }

    public void setContext(Context context){
        this.context=context;
    }
    public ThisWeekend_Adapter setting_thisweekend_adapter(){
        thisweekends=new ArrayList<>();
        thisWeekend_adapter=new ThisWeekend_Adapter(context, thisweekends);
        thisWeekend_adapter.setOnSelectItemCLickListener(new ThisWeekend_Adapter.OnSelectItemClickListener() {
            @Override
            public void OnSelectItemClick(View v, int pos, int pk) {

            }
        });
        return thisWeekend_adapter;
    }

    public void getMain_thisweekends(){
        TotalApiClient.getApiService(context).get_thisweekend_post(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lists -> {
                            main_thisweekends_post_cnt.setValue(lists.getData().size());
                            Type type=new TypeToken<ArrayList<Thisweekend>>(){}.getType();
                            String result=new Gson().toJson(lists.getData());
                            thisweekends=new Gson().fromJson(result, type);
                            thisWeekend_adapter.update_Item(thisweekends);
                        },
                        error -> {
                            System.out.println(error);
                        }
                );
    }

    public Az_Adapter setting_az_adapter(){
        azPosts=new ArrayList<>();
        az_adapter=new Az_Adapter(context, azPosts);
        az_adapter.setOnSelectItemCLickListener(new Az_Adapter.OnSelectItemClickListener() {
            @Override
            public void OnSelectItemClick(View v, int pos) {

            }
        });
        return az_adapter;
    }
    public void getAz(){

    }

    public NewCarpingPlace_Adapter setting_newcarping_place_adapter(){
        newCarpingPlace_adapter=new NewCarpingPlace_Adapter(context);
        newCarpingPlace_adapter.setOnSelectItemCLickListener(new NewCarpingPlace_Adapter.OnSelectItemClickListener() {
            @Override
            public void OnSelectItemClick(View v, int pos) {

            }
        });
        return newCarpingPlace_adapter;
    }
    public void getNewCarpingPlace(){}

    public PopularCarpingPlace_Adapter setting_popularcarping_place_adapter(){
        popularCarpingPlace_adapter=new PopularCarpingPlace_Adapter(context);
        return popularCarpingPlace_adapter;
    }
    public void getPopularCarpingPlace(){}
}
