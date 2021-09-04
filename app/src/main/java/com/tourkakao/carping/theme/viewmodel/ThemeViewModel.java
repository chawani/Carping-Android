package com.tourkakao.carping.theme.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.theme.Activity.ThemeDetailActivity;
import com.tourkakao.carping.theme.Adapter.ThemeAdapter;
import com.tourkakao.carping.theme.Dataclass.FilterTheme;
import com.tourkakao.carping.theme.Dataclass.Theme;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThemeViewModel extends ViewModel {
    private Context context;
    ArrayList<Theme> themes=null;
    ThemeAdapter themeAdapter;
    public ThemeViewModel(){

    }
    public void setContext(Context context){
        this.context=context;
    }
    public ThemeAdapter setting_theme_adapter(){
        themes=new ArrayList<>();
        themeAdapter=new ThemeAdapter(context, themes);
        themeAdapter.setOnSelectItemClickListener(new ThemeAdapter.OnSelectItemClickListener() {
            @Override
            public void OnSelectItemClick(View v, int pos, int pk) {
                Intent intent=new Intent(context, ThemeDetailActivity.class);
                intent.putExtra("name", themes.get(pos).getName());
                intent.putExtra("pk", pk);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return themeAdapter;
    }
    public void get_each_thema_carpingplace(String theme, String sort, String select, double lat, double lon){
        System.out.println(theme+" "+sort+" "+select+" "+lat+" "+lon);
        FilterTheme filterTheme=new FilterTheme(theme, sort, select, lat, lon);
        TotalApiClient.getApiService(context).get_thema_carping(filterTheme)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lists -> {
                            if(lists.isSuccess()) {
                                Type type = new TypeToken<ArrayList<Theme>>() {
                                }.getType();
                                String result = new Gson().toJson(lists.getData());
                                themes = new Gson().fromJson(result, type);
                                themeAdapter.update_Item(themes);
                            }else{
                                System.out.println(lists.getError_message());
                            }
                        },
                        error -> {
                            System.out.println(error);
                        }
                );
    }

    public void setting_theme_bookmark(int pk){
        TotalApiClient.getApiService(context).set_theme_bookmark(pk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            if(!result.isSuccess()){
                                System.out.println(result.getError_message());
                            }
                        },
                        error -> {}
                );
    }
    public void releasing_theme_bookmark(int pk){
        TotalApiClient.getApiService(context).release_theme_bookmark(pk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            if(!result.isSuccess()){
                                System.out.println(result.getError_message());
                            }
                        },
                        error -> {}
                );
    }
}
