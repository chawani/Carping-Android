package com.tourkakao.carping.NetworkwithToken;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface PostInterface {
    @POST("/posts/user-post")
    Single<CommonClass> getPostList(@Body HashMap<String,Object> type);

    @GET("/posts/user-post/info/{pk}")
    Single<CommonClass> getPostInfoDetail(@Path("pk")int pk);

    @POST("/posts/user-post/like")
    Single<CommonClass> postLike(@Body HashMap<String,Object> likeMap);

    @HTTP(method = "DELETE", path = "/posts/user-post/like", hasBody = true)
    Single<CommonClass> cancelLike(@Body HashMap<String,Object> likeMap);

    @POST("/search/posts")
    Single<CommonClass> search(@Body HashMap<String,Object> map);

    @POST("/search/complete")
    Single<CommonClass> completeSearch(@Body HashMap<String,Object> map);

    @POST("/search/keyword")
    Single<CommonClass> getKeywordList(@Body HashMap<String,Object> map);

    @POST("/comments/review/")
    Single<CommonClass> postReview(@Body HashMap<String,Object> map);

    @DELETE("/comments/review/{id}/")
    Single<CommonClass> deleteReview(@Path("id")int id);

    @POST("/posts/user-post/info/{pk}/reviews")
    Single<CommonClass> getReviewTotal(@Path("pk")int pk,@Body HashMap<String,String> map);

    @POST("/comments/review/like")
    Single<CommonClass> likeReview(@Body HashMap<String,Object> map);

    @HTTP(method = "DELETE", path = "/comments/review/like", hasBody = true)
    Single<CommonClass> cancelReviewLike(@Body HashMap<String,Object> map);

    @GET("/posts/user-post/{pk}")
    Single<CommonClass> getPostDetail(@Path("pk")int pk);

    @POST("/posts/user-post/{id}/payment-ready")
    Single<CommonClass> readyPayment(@Path("id")int id);

    @Multipart
    @POST("/posts/user-post/create")
    Single<CommonClass> writePost( @Part MultipartBody.Part thumbnail,
                                   @Part MultipartBody.Part image1,
                                   @Part MultipartBody.Part image2,
                                   @Part MultipartBody.Part image3,
                                   @Part MultipartBody.Part image4,
                                   @Part MultipartBody.Part image5,
                                   @PartMap HashMap<String, RequestBody> data);

    @GET("/posts/user-post/pre-create")
    Single<CommonClass> getChannelInfo();

    @GET("/posts/user-post/info/{id}/free")
    Single<CommonClass> payFreePost(@Path("id")int id);

    @POST("/posts/user-post/compute-fee")
    Single<CommonClass> getCalcResult(@Body HashMap<String,Object> map);

    @POST("/posts/user-post/{id}/deactivate")
    Single<CommonClass> deactivatePost(@Path("id")int id);
}
