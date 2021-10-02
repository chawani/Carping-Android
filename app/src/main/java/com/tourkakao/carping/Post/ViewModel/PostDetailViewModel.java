package com.tourkakao.carping.Post.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.EcoCarping.DTO.LikeResponse;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.Post.DTO.PayResponse;
import com.tourkakao.carping.Post.DTO.PostDetail;
import com.tourkakao.carping.Post.DTO.PostInfoDetail;
import com.tourkakao.carping.Post.DTO.PostListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PostDetailViewModel extends ViewModel {
    private Context context;
    private MutableLiveData<PostInfoDetail> postInfo=new MutableLiveData<>();
    private MutableLiveData<PostDetail> post=new MutableLiveData<>();
    private MutableLiveData<PayResponse> payResponse=new MutableLiveData<>();
    private MutableLiveData<String> likeStatus=new MutableLiveData<>();
    private int pk;
    private  Gson gson=new Gson();

    public void setContext(Context context){
        this.context=context;
    }

    public MutableLiveData<PostInfoDetail> getPostInfo(){
        return postInfo;
    }
    public MutableLiveData<PostDetail> getPost(){return post;}
    public MutableLiveData<String> getLikeStatus(){return likeStatus;}
    public MutableLiveData<PayResponse> getPayResponse(){return payResponse;}
    public void setPk(int pk){this.pk=pk;}

    public void setInfoData(List data){
        String total=gson.toJson(data.get(0));
        PostInfoDetail item =gson.fromJson(total,PostInfoDetail.class);
        postInfo.setValue(item);
        likeStatus.setValue(item.getIs_liked());
    }

    public void setLikeDataPush(String total){
        Gson gson=new Gson();
        LikeResponse message=gson.fromJson(total, LikeResponse.class);
        if(message.getMessage().equals("포스트 좋아요 완료"))
            likeStatus.setValue("true");
    }

    public void setLikeDataCancel(String total){
        Gson gson=new Gson();
        LikeResponse message=gson.fromJson(total, LikeResponse.class);
        if(message.getMessage().equals("포스트 좋아요 취소"))
            likeStatus.setValue("false");
    }

    public void loadInfoDetail(){
        TotalApiClient.getPostApiService(context).getPostInfoDetail(pk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            setInfoData(commonClass.getData());
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

    public void postLike(int pk){
        HashMap<String,Object> map=new HashMap<>();
        map.put("post_to_like",pk);
        TotalApiClient.getPostApiService(context).postLike(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            setLikeDataPush(new Gson().toJson(commonClass.getData().get(0)));
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

    public void cancelLike(int pk){
        HashMap<String,Object> map=new HashMap<>();
        map.put("post_to_like",pk);
        TotalApiClient.getPostApiService(context).cancelLike(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        setLikeDataCancel(new Gson().toJson(commonClass.getData().get(0)));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
    }

    public void deleteComment(int pk){
        TotalApiClient.getPostApiService(context).deleteReview(pk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    void setPostData(List data){
        String total=gson.toJson(data.get(0));
        PostDetail item =gson.fromJson(total, PostDetail.class);
        post.setValue(item);
        System.out.println(total);
    }

    public void loadPostDetail(int pk){
        TotalApiClient.getPostApiService(context).getPostDetail(pk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            setPostData(commonClass.getData());
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

    void setPayData(List data){
        String total=gson.toJson(data.get(0));
        PayResponse item =gson.fromJson(total, PayResponse.class);
        payResponse.setValue(item);
        System.out.println(total);
    }

    public void readyPayment(int id){
        TotalApiClient.getPostApiService(context).readyPayment(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            setPayData(commonClass.getData());
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
