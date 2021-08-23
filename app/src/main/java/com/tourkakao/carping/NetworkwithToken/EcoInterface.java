package com.tourkakao.carping.NetworkwithToken;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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
}
