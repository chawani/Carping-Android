package com.tourkakao.carping.NetworkwithToken;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MypageInterface {
    @FormUrlEncoded
    @POST("mypage/sort")
    Single<CommonClass> getMypageActivities(@Field("sort")String sort,@Field("subsort")String subsort);

    @FormUrlEncoded
    @POST("mypage/sort")
    Single<CommonClass> getScrap(@Field("sort")String sort,@Field("subsort")String subsort,@Field("lat")String lat,@Field("lon")String lon);
}
