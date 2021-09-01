package com.tourkakao.carping.EcoCarping.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.EcoCarping.DTO.Comment;
import com.tourkakao.carping.EcoCarping.DTO.EcoPost;
import com.tourkakao.carping.EcoCarping.DTO.PostComment;
import com.tourkakao.carping.Home.EcoDataClass.EcoReview;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.RequestBody;

public class EcoDetailViewModel  extends ViewModel {
    private Context context;
    private int pk;
    private MutableLiveData<EcoPost> post=new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> images=new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> tags=new MutableLiveData<>();
    private MutableLiveData<ArrayList<Comment>> comments=new MutableLiveData<>();
    private ArrayList<Comment> commentArray;

    public void setContext(Context context){
        this.context=context;
    }

    public void setPk(int pk){this.pk=pk;}

    public MutableLiveData<EcoPost> getPost(){return post;}

    public MutableLiveData<ArrayList<String>> getImages(){return images;}

    public MutableLiveData<ArrayList<String>> getTags(){return tags;}

    public MutableLiveData<ArrayList<Comment>> getComments(){return comments;}

    public void setPostData(String total){
        System.out.println(total);
        Gson gson=new Gson();
        EcoPost detail=gson.fromJson(total, EcoPost.class);
        post.setValue(detail);
        ArrayList<String> imgArray=new ArrayList<>();
        if(detail.getImage1()!=null){
        imgArray.add(detail.getImage1());}
        if(detail.getImage2()!=null){
            imgArray.add(detail.getImage2());}
        if(detail.getImage3()!=null){
            imgArray.add(detail.getImage3());}
        if(detail.getImage4()!=null){
            imgArray.add(detail.getImage4());}
        images.setValue(imgArray);

        //String tagString=gson.toJson(detail.getTags());
        ArrayList<String> tagArray=new ArrayList<>();//=gson.fromJson(tagString, new TypeToken<ArrayList<String>>(){}.getType());
        for(String s:detail.getTags()){
            tagArray.add(s);
        }
        tags.setValue(tagArray);

        commentArray=detail.getComment();
        comments.setValue(commentArray);
    }

    public void loadDetail(){
        TotalApiClient.getEcoApiService(context).getEcoCarpingDetail(pk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        String totalString=new Gson().toJson(commonClass.getData().get(0));
                        setPostData(totalString);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void setCommentData(String total){
        Gson gson=new Gson();
        Comment comment=gson.fromJson(total,Comment.class);
        commentArray.add(comment);
        comments.setValue(commentArray);
    }

    public void updateComments(PostComment comment){
        TotalApiClient.getEcoApiService(context).postComment(comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        String totalString=new Gson().toJson(commonClass.getData().get(0));
                        setCommentData(totalString);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("post 실패"+e.getMessage());

                    }
                });
    }
}
