package com.tourkakao.carping.sharedetail.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.Home.ShareDataClass.Share;
import com.tourkakao.carping.Home.ShareFragmentAdapter.ShareAdapter;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.sharedetail.Activity.ShareDetailActivity;
import com.tourkakao.carping.sharedetail.Adapter.CommentAdapter;
import com.tourkakao.carping.sharedetail.DataClass.Comment;
import com.tourkakao.carping.sharedetail.DataClass.ShareDetail;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ShareDetailViewModel extends ViewModel {
    Context context;
    ShareAdapter shareAdapter;
    CommentAdapter commentAdapter;
    ArrayList<Comment> comments=null;
    ArrayList<Share> shares=null;
    int userpk;
    private MutableLiveData<ArrayList<String>> images=new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> tags=new MutableLiveData<>();
    private MutableLiveData<ShareDetail> detail=new MutableLiveData<>();
    public MutableLiveData<Integer> share_delete=new MutableLiveData<>();
    public MutableLiveData<Integer> share_complete=new MutableLiveData<>();
    public int to_share_detail;
    public boolean isdetail;
    public ShareDetailViewModel(){
        share_delete.setValue(0);
        to_share_detail=0;
        isdetail=false;
    }
    public void setContext(Context context){
        this.context=context;
    }
    public void setUserpk(int userpk) {
        this.userpk = userpk;
    }

    public ShareAdapter setting_share_adapter(){
        shares=new ArrayList<>();
        shareAdapter=new ShareAdapter(context, shares);
        shareAdapter.setOnSelectItemClickListener(new ShareAdapter.OnSelectItemClickListener() {
            @Override
            public void OnSelectItemClick(View v, int pos, int pk) {
                to_share_detail=1;
                isdetail=true;
                Intent intent=new Intent(context, ShareDetailActivity.class);
                intent.putExtra("pk", pk);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return shareAdapter;
    }
    public CommentAdapter setting_comment_adapter(){
        comments=new ArrayList<>();
        commentAdapter=new CommentAdapter(context, comments, userpk);
        return commentAdapter;
    }
    public void get_total_share(String sort, int count){
        TotalApiClient.getCommunityApiService(context).get_share(sort, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lists -> {
                            if(lists.isSuccess()){
                                Type type=new TypeToken<ArrayList<Share>>(){}.getType();
                                String result=new Gson().toJson(lists.getData());
                                shares=new Gson().fromJson(result, type);
                                if(sort.equals("recent")) {
                                    shares.remove(0);
                                }
                                shareAdapter.update_Item(shares);

                            }else{
                                System.out.println(lists.getError_message());
                            }
                        },
                        error -> {

                        }
                );
    }
    public void get_share_detail(int id){
        TotalApiClient.getCommunityApiService(context).get_share_detail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> {
                            if(list.isSuccess()){
                                String result=new Gson().toJson(list.getData().get(0));
                                setShareDetail(result);
                            }else{
                                System.out.println(list.getError_message());
                            }
                        },
                        error -> {

                        }
                );
    }
    public void share_like(int pk){
        TotalApiClient.getCommunityApiService(context).share_like(pk)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
    public void share_release_like(int pk){
        TotalApiClient.getCommunityApiService(context).share_release_like(pk)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
    public void share_delete(int pk){
        TotalApiClient.getCommunityApiService(context).delete_share(pk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        res -> {
                            if(res.isSuccess()) {
                                share_delete.setValue(1);
                            }
                        },
                        error -> {
                        }
                );
    }
    public void share_complete(int pk){
        TotalApiClient.getCommunityApiService(context).share_complete(pk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        res -> {
                            if(res.isSuccess()){
                                share_complete.setValue(1);
                            }
                        },
                        error -> {}
                );
    }
    public void share_cancel_complete(int pk){
        TotalApiClient.getCommunityApiService(context).share_cancel_complete(pk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        res -> {
                            if(res.isSuccess()){
                                share_complete.setValue(0);
                            }
                        },
                        error -> {}
                );
    }
    public void send_comment(int id, String text){
        TotalApiClient.getCommunityApiService(context).send_share_comment(userpk, id, text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        res -> {
                            if(res.isSuccess()){
                                Type type=new TypeToken<Comment>(){}.getType();
                                String result=new Gson().toJson(res.getData().get(0));
                                Comment comment=new Gson().fromJson(result, type);
                                comments.add(comment);
                                commentAdapter.updateItem(comments);
                            }
                        },
                        error -> {

                        }
                );
    }
    public void setShareDetail(String result){
        ShareDetail shareDetail=new Gson().fromJson(result, ShareDetail.class);
        detail.setValue(shareDetail);
        ArrayList<String> imgarr=new ArrayList<>();
        if(shareDetail.getImage1()!=null){
            imgarr.add(shareDetail.getImage1());
        }
        if(shareDetail.getImage2()!=null){
            imgarr.add(shareDetail.getImage2());
        }
        if(shareDetail.getImage3()!=null){
            imgarr.add(shareDetail.getImage3());
        }
        if(shareDetail.getImage4()!=null){
            imgarr.add(shareDetail.getImage4());
        }
        for(int i=0; i<imgarr.size(); i++){
            System.out.println(imgarr.get(i));
        }
        images.setValue(imgarr);
        ArrayList<String> tagarr=new ArrayList<>();
        for(int i=0; i<shareDetail.getTags().size(); i++){
            tagarr.add(shareDetail.getTags().get(i));
        }
        if(shareDetail.isIs_shared()==true){
            share_complete.setValue(1);
        }else{
            share_complete.setValue(0);
        }
        tags.setValue(tagarr);
        comments=shareDetail.getComment();
        commentAdapter.updateItem(comments);
    }
    public MutableLiveData<ArrayList<String>> getImages() {
        return images;
    }

    public MutableLiveData<ArrayList<String>> getTags() {
        return tags;
    }

    public MutableLiveData<ShareDetail> getDetail() {
        return detail;
    }
}
