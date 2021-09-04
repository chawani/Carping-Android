package com.tourkakao.carping.EcoCarping.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.tourkakao.carping.EcoCarping.DTO.Comment;
import com.tourkakao.carping.EcoCarping.DTO.EcoPost;
import com.tourkakao.carping.EcoCarping.DTO.LikeResponse;
import com.tourkakao.carping.EcoCarping.DTO.PostComment;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EcoDetailViewModel  extends ViewModel {
    private Context context;
    private int pk;
    private MutableLiveData<EcoPost> post=new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> images=new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> tags=new MutableLiveData<>();
    private MutableLiveData<ArrayList<Comment>> comments=new MutableLiveData<>();
    private ArrayList<Comment> commentArray;
    private MutableLiveData<String> likeStatus=new MutableLiveData<>();

    public void setContext(Context context){
        this.context=context;
    }

    public void setPk(int pk){this.pk=pk;}

    public MutableLiveData<EcoPost> getPost(){return post;}

    public MutableLiveData<ArrayList<String>> getImages(){return images;}

    public MutableLiveData<ArrayList<String>> getTags(){return tags;}

    public MutableLiveData<ArrayList<Comment>> getComments(){return comments;}

    public MutableLiveData<String> getLikeStatus(){return likeStatus;}

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

        ArrayList<String> tagArray=new ArrayList<>();
        for(String s:detail.getTags()){
            tagArray.add(s);
        }
        tags.setValue(tagArray);

        commentArray=detail.getComment();
        comments.setValue(commentArray);

        likeStatus.setValue(String.valueOf(detail.getIs_liked()));
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

    public void pushLike(){
        HashMap<String,Object> map=new HashMap<>();
        int id=(int)Double.parseDouble(post.getValue().getId());

        map.put("post_to_like",id);
        TotalApiClient.getEcoApiService(context).postLike(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        setLikeDataPush(new Gson().toJson(commonClass.getData().get(0)));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
    }

    public void cancelLike(){
        HashMap<String,Object> map=new HashMap<>();
        int id=(int)Double.parseDouble(post.getValue().getId());

        map.put("post_to_like",id);
        TotalApiClient.getEcoApiService(context).cancelLike(map)
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

    public void deletePost(){
        int id=(int)Double.parseDouble(post.getValue().getId());
        TotalApiClient.getEcoApiService(context).deletePost(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        System.out.println("삭제:"+commonClass.getCode());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void deleteComment(int pk){
        TotalApiClient.getEcoApiService(context).deleteComment(pk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        System.out.println("삭제:"+commonClass.getCode());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
