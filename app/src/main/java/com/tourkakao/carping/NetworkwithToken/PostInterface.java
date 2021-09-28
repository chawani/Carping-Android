package com.tourkakao.carping.NetworkwithToken;

import java.util.HashMap;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostInterface {
    @POST("/posts/user-post")
    Single<CommonClass> getPostList(@Body HashMap<String,Object> type);

    @GET("/posts/user-post/info/{pk}")
    Single<CommonClass> getPostInfoDetail(@Path("pk")int pk);
}
