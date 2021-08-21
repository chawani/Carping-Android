package com.tourkakao.carping.NetworkwithToken;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ThemeInterface {
    @FormUrlEncoded
    @POST("posts/autocamp/weekend-post")
    Single<CommonClass> get_thisweekend_post(@Field("count")int count);

    @FormUrlEncoded
    @POST("accounts/token/refresh")
    Single<AccessToken> getNewtoken(@Field("refresh_token")String refresh_token);

    @GET("posts/autocamp/{pk}")
    Single<CommonClass> get_each_thisweekend_detail(@Path("pk")int pk);

    @FormUrlEncoded
    @POST("camps/auto-camp/partial")
    Single<CommonClass> get_newcarping_place(@Field("count")int count);

    @GET("camps/auto-camp/{pk}")
    Single<CommonClass> get_each_newcarping_place_detail(@Path("pk")int pk);

    @FormUrlEncoded
    @POST("camps/auto-camp/bookmark")
    Single<CommonClass> set_bookmark(@Field("autocamp_to_bookmark")int autocamp_to_bookmark);

    @FormUrlEncoded
    @HTTP(method="DELETE", path="camps/auto-camp/bookmark", hasBody = true)
    Single<CommonClass> release_bookmark(@Field("autocamp_to_bookmark")int autocamp_to_bookmark);

}
