package com.tourkakao.carping.NetworkwithToken;

import com.tourkakao.carping.registernewcarping.DataClass.CarpingSearchKeyword;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.processors.MulticastProcessor;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface RegisterInterface {
    @Multipart
    @POST("camps/auto-camp/")
    Single<CommonClass> send_newcarping(@Part MultipartBody.Part image1,
                                        @Part MultipartBody.Part image2,
                                        @Part MultipartBody.Part image3,
                                        @Part MultipartBody.Part image4,
                                        @PartMap HashMap<String, RequestBody> data,
                                        @Part("latitude")float latitude,
                                        @Part("longitude")float longitude);

    @GET("v2/local/search/keyword.json")
    Single<CarpingSearchKeyword> getSearchKeyword(@Header("Authorization")String key, @Query("query")String query);
}
