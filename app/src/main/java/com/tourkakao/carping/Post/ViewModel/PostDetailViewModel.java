package com.tourkakao.carping.Post.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.Post.DTO.PostInfoDetail;
import com.tourkakao.carping.Post.DTO.PostListItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PostDetailViewModel extends ViewModel {
    private Context context;
    private MutableLiveData<PostInfoDetail> postInfo=new MutableLiveData<>();
    public void setContext(Context context){
        this.context=context;
    }

    public MutableLiveData<PostInfoDetail> getPostInfo(){
        return postInfo;
    }

    public void setData(List data){
        System.out.println("포스트 인포");
        Gson gson=new Gson();
        String total=gson.toJson(data);
        System.out.println("포스트 인포"+total);
        PostInfoDetail item =gson.fromJson(total,PostInfoDetail.class);
        postInfo.setValue(item);
    }

    public void loadInfoDetail(int pk){
        TotalApiClient.getPostApiService(context).getPostInfoDetail(pk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            setData(commonClass.getData());
                        }
                        else {
                            System.out.println("요청실패:"+commonClass.getCode()+commonClass.getError_message());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("에러"+e.getMessage());
                    }
                });
    }
}
