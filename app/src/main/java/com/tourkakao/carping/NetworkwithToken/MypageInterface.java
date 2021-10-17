package com.tourkakao.carping.NetworkwithToken;

import java.util.HashMap;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface MypageInterface {
    @FormUrlEncoded
    @POST("mypage/sort")
    Single<CommonClass> getMypageActivities(@Field("sort")String sort,@Field("subsort")String subsort);

    @FormUrlEncoded
    @POST("mypage/sort")
    Single<CommonClass> getScrap(@Field("sort")String sort,@Field("subsort")String subsort,@Field("lat")String lat,@Field("lon")String lon);

    @GET("mypage/profile/{id}")
    Single<CommonClass> getMypageInfo(@Path("id")int id);

    @Multipart
    @PATCH("mypage/profile/{id}/")
    Single<CommonClass> postProfileImg(@Path("id")String id,@Part MultipartBody.Part image);

    @Multipart
    @PATCH("mypage/profile/{id}/")
    Single<CommonClass> postUserInfo(@Path("id")String id,@PartMap HashMap<String, RequestBody> data);

    @POST("/accounts/send-sms")
    Single<CommonClass> postPhoneNumber(@Body HashMap<String,Object> phone);

    @POST("/accounts/sms-verification")
    Single<CommonClass> postverificationNumber(@Body HashMap<String,Object> auth_num);

    @POST("mypage/post-status")
    Single<CommonClass> getPostStatus(@Body HashMap<String,Object> map);

    @POST("accounts/withdrawal")
    Single<CommonClass> withdrawAccount();
}
