package com.tourkakao.carping.NetworkwithToken;

import java.util.HashMap;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostInterface {
    @POST("/posts/user-post")
    Single<CommonClass> getPostList(@Body HashMap<String,Object> type);
}
