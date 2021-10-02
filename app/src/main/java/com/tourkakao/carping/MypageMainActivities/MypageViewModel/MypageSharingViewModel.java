package com.tourkakao.carping.MypageMainActivities.MypageViewModel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.Home.EcoDataClass.EcoReview;
import com.tourkakao.carping.Home.ShareDataClass.Share;
import com.tourkakao.carping.Home.ShareFragmentAdapter.ShareAdapter;
import com.tourkakao.carping.Home.ThemeDataClass.AZPost;
import com.tourkakao.carping.Home.ThemeFragmentAdapter.Az_Adapter;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.Post.PostDetailActivity;
import com.tourkakao.carping.sharedetail.Activity.ShareDetailActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MypageSharingViewModel extends ViewModel {
    private Gson gson=new Gson();
    private Context context;
    private MutableLiveData<ArrayList<Share>> myShare=new MutableLiveData<>();
    private MutableLiveData<ArrayList<Share>> likeShare=new MutableLiveData<>();
    private ArrayList<Share> myShareArray=new ArrayList<>();
    private ArrayList<Share> likeShareArray=new ArrayList<>();
    private ShareAdapter adapter;

    public void setContext(Context context){
        this.context=context;
    }
    public MutableLiveData<ArrayList<Share>> getMyShare(){return myShare;}
    public MutableLiveData<ArrayList<Share>> getLikeShare(){return likeShare;}

    public void setLikeData(ArrayList datas){
        String totalDataString=gson.toJson(datas);
        ArrayList<Share> shares=gson.fromJson(totalDataString,new TypeToken<ArrayList<Share>>(){}.getType());
        likeShare.setValue(shares);
    }

    public void getSharePosts(String subsort){
        TotalApiClient.getMypageApiService(context).getMypageActivities("share",subsort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            if(subsort.equals("my")){
                                Type type = new TypeToken<ArrayList<Share>>(){}.getType();
                                String result = new Gson().toJson(commonClass.getData());
                                myShareArray = new Gson().fromJson(result, type);
                                myShare.setValue(myShareArray);
                                adapter.update_Item(myShareArray);
                            }
                            if(subsort.equals("like")){
                                Type type = new TypeToken<ArrayList<Share>>(){}.getType();
                                String result = new Gson().toJson(commonClass.getData());
                                likeShareArray = new Gson().fromJson(result, type);
                                likeShare.setValue(likeShareArray);
                                adapter.update_Item(likeShareArray);
                            }
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

    public ShareAdapter settingMyAdapter(){
        adapter=new ShareAdapter(context, myShareArray);
        adapter.setOnSelectItemClickListener(new ShareAdapter.OnSelectItemClickListener() {
            @Override
            public void OnSelectItemClick(View v, int pos, int pk) {
                Intent intent=new Intent(context, ShareDetailActivity.class);
                intent.putExtra("pk", pk);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return adapter;
    }

    public ShareAdapter settingLikeAdapter(){
        adapter=new ShareAdapter(context, likeShareArray);
        adapter.setOnSelectItemClickListener(new ShareAdapter.OnSelectItemClickListener() {
            @Override
            public void OnSelectItemClick(View v, int pos, int pk) {
                Intent intent=new Intent(context, ShareDetailActivity.class);
                intent.putExtra("pk", pk);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return adapter;
    }
}
