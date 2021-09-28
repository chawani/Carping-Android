package com.tourkakao.carping.NetworkwithToken;

import com.google.gson.JsonObject;
import com.tourkakao.carping.EcoCarping.DTO.PostComment;
import com.tourkakao.carping.EcoCarping.DTO.ResultSearchKeyword;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EcoInterface {
    @FormUrlEncoded
    @POST("posts/eco-carping/sort")
    Single<CommonClass> getRecentEcoCarpingReview(@Field("sort")String sort,@Field("count")int count);

    @FormUrlEncoded
    @POST("posts/eco-carping/sort")
    Single<CommonClass> getPopularEcoCarpingReview(@Field("sort")String sort);

    @GET("accounts/eco-ranking")
    Single<CommonClass> getEcoCarpingRanking();

    @GET("v2/local/search/keyword.json")
    Single<ResultSearchKeyword> getSearchKeyword(@Header("Authorization")String key, @Query("query")String query);

    @GET("posts/eco-carping/{pk}")
    Single<CommonClass> getEcoCarpingDetail(@Path("pk")int pk);

    @Multipart
    @POST("/posts/eco-carping/")
    Single<CommonClass> postReview(
            @Part MultipartBody.Part image1,
            @Part MultipartBody.Part image2,
            @Part MultipartBody.Part image3,
            @Part MultipartBody.Part image4,
            @PartMap HashMap<String, RequestBody> data);

    @POST("/comments/comment/")
    Single<CommonClass> postComment(@Body PostComment comment);

    @POST("/posts/eco-carping/like")
    Single<CommonClass> postLike(@Body HashMap<String,Object> likeMap);

    @HTTP(method = "DELETE", path = "/posts/eco-carping/like", hasBody = true)
    Single<CommonClass> cancelLike(@Body HashMap<String,Object> likeMap);

    @Multipart
    @PATCH("/posts/eco-carping/{id}/")
    Single<CommonClass> editPost(@Path("id")int id,
                                 @Part MultipartBody.Part image1,
                                 @Part MultipartBody.Part image2,
                                 @Part MultipartBody.Part image3,
                                 @Part MultipartBody.Part image4,
                                 @PartMap HashMap<String, RequestBody> data,
                                 @Part("is_null") ArrayList<Integer> is_null);

    @DELETE("/posts/eco-carping/{id}/")
    Single<CommonClass> deletePost(@Path("id")int id);

//    @Multipart
//    @PATCH("/comments/comment/{id}/")
//    Single<CommonClass> editComment(@Path("id")int id,@PartMap HashMap<String, RequestBody> data);

    @DELETE("/comments/comment/{id}/")
    Single<CommonClass> deleteComment(@Path("id")int id);
}
