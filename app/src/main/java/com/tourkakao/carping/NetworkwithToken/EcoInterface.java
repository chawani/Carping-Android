package com.tourkakao.carping.NetworkwithToken;

import com.tourkakao.carping.EcoCarping.ResultSearchKeyword;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EcoInterface {
    @FormUrlEncoded
    @POST("posts/eco-carping/sort")
    Single<CommonClass> getRecentEcoCarpingReview(@Field("sort")String sort,@Field("count")int count);

    @FormUrlEncoded
    @POST("posts/eco-carping/sort")
    Single<CommonClass> getPopularEcoCarpingReview(@Field("sort")String sort);

    @FormUrlEncoded
    @POST("posts/eco-carping/sort")
    Single<CommonClass> getDistanceEcoCarpingReview(@Field("sort")String sort,@Field("latitude")Float latitude,@Field("longitude")Float longitude);

    @GET("accounts/eco-ranking")
    Single<CommonClass> getEcoCarpingRanking();

    @GET("v2/local/search/keyword.json")
    Single<ResultSearchKeyword> getSearchKeyword(@Header("Authorization")String key, @Query("query")String query);

    @GET("posts/eco-carping/{pk}")
    Single<CommonClass> getEcoCarpingDetail(@Path("pk")int pk);
}
