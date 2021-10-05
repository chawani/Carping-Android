package com.tourkakao.carping.MypageMainActivities.MypageViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.Home.EcoDataClass.EcoReview;
import com.tourkakao.carping.MypageMainActivities.DTO.Post;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.Post.DTO.PostListItem;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MypagePostViewModel extends ViewModel {
    private Gson gson=new Gson();
    private Context context;
    private MutableLiveData<ArrayList<Post>> publish=new MutableLiveData<>();
    private MutableLiveData<ArrayList<Post>> purchase=new MutableLiveData<>();
    private MutableLiveData<ArrayList<Post>> like=new MutableLiveData<>();
    private MutableLiveData<ArrayList<Post>> beforeApproval=new MutableLiveData<>();
    private MutableLiveData<ArrayList<Post>> afterApproval=new MutableLiveData<>();
    private MutableLiveData<ArrayList<Post>> deposit=new MutableLiveData<>();

    public void setContext(Context context){
        this.context=context;
    }
    public MutableLiveData<ArrayList<Post>> getPublish(){return publish;}
    public MutableLiveData<ArrayList<Post>> getPurchase(){return purchase;}
    public MutableLiveData<ArrayList<Post>> getLike(){return like;}
    public MutableLiveData<ArrayList<Post>> getBeforeApproval(){return beforeApproval;}
    public MutableLiveData<ArrayList<Post>> getAfterApproval(){return afterApproval;}
    public MutableLiveData<ArrayList<Post>> getDeposit(){return deposit;}

    public void setMyPostData(ArrayList datas){
        String totalDataString=gson.toJson(datas);
        ArrayList<Post> list=gson.fromJson(totalDataString,new TypeToken<ArrayList<Post>>(){}.getType());
        publish.setValue(list);
    }

    public void setPurchasePostData(ArrayList datas){
        String totalDataString=gson.toJson(datas);
        ArrayList<Post> list=gson.fromJson(totalDataString,new TypeToken<ArrayList<Post>>(){}.getType());
        purchase.setValue(list);
    }

    public void setLikePostData(ArrayList datas){
        String totalDataString=gson.toJson(datas);
        ArrayList<Post> list=gson.fromJson(totalDataString,new TypeToken<ArrayList<Post>>(){}.getType());
        like.setValue(list);
    }

    public void setBeforeApprovalData(ArrayList datas){
        String totalDataString=gson.toJson(datas);
        ArrayList<Post> list=gson.fromJson(totalDataString,new TypeToken<ArrayList<Post>>(){}.getType());
        beforeApproval.setValue(list);
    }
    public void setAfterApprovalData(ArrayList datas){
        String totalDataString=gson.toJson(datas);
        ArrayList<Post> list=gson.fromJson(totalDataString,new TypeToken<ArrayList<Post>>(){}.getType());
        afterApproval.setValue(list);
    }
    public void setDepositData(ArrayList datas){
        String totalDataString=gson.toJson(datas);
        ArrayList<Post> list=gson.fromJson(totalDataString,new TypeToken<ArrayList<Post>>(){}.getType());
        deposit.setValue(list);
    }

    public void loadPosts(String subsort){
        TotalApiClient.getMypageApiService(context).getMypageActivities("post",subsort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            if(subsort.equals("my"))
                                setMyPostData(commonClass.getData());
                            if(subsort.equals("buy"))
                                setPurchasePostData(commonClass.getData());
                            if(subsort.equals("like"))
                                setLikePostData(commonClass.getData());
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

    public void loadApproval(int sort){
        HashMap<String,Object> map=new HashMap<>();
        map.put("sort",sort);
        TotalApiClient.getMypageApiService(context).getPostStatus(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            if(sort==0)
                                setBeforeApprovalData(commonClass.getData());
                            if(sort==1)
                                setAfterApprovalData(commonClass.getData());
                            if(sort==2)
                                setDepositData(commonClass.getData());
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
