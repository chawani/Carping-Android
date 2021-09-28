package com.tourkakao.carping.Home.CommunityViewModel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.Home.ShareDataClass.Share;
import com.tourkakao.carping.Home.ShareFragmentAdapter.ShareAdapter;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.sharedetail.Activity.ShareDetailActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ShareViewModel extends ViewModel {
    private Context context;
    ShareAdapter shareAdapter;
    ArrayList<Share> shares=null;
    public MutableLiveData<String> share_count=new MutableLiveData<>();
    public ShareViewModel(){
        share_count.setValue("0개 나눔글이 있습니다");
    }
    public void setContext(Context context){
        this.context=context;
    }
    public ShareAdapter setting_share_adapter(){
        shares=new ArrayList<>();
        shareAdapter=new ShareAdapter(context, shares);
        shareAdapter.setOnSelectItemClickListener(new ShareAdapter.OnSelectItemClickListener() {
            @Override
            public void OnSelectItemClick(View v, int pos, int pk) {
                Intent intent=new Intent(context, ShareDetailActivity.class);
                intent.putExtra("pk", pk);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return shareAdapter;
    }
    public void get_share(String sort, int count){
        TotalApiClient.getCommunityApiService(context).get_share(sort, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lists -> {
                            if(lists.isSuccess()){
                                Type type=new TypeToken<ArrayList<Share>>(){}.getType();
                                String result=new Gson().toJson(lists.getData());
                                shares=new Gson().fromJson(result, type);
                                share_count.setValue(shares.get(0).getTotal_share()+"개 나눔글이 있습니다");
                                shares.remove(0);
                                shareAdapter.update_Item(shares);

                            }else{
                                System.out.println(lists.getError_message());
                            }
                        },
                        error -> {

                        }
                );
    }
}
