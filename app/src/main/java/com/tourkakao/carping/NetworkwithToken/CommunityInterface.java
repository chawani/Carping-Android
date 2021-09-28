package com.tourkakao.carping.NetworkwithToken;

import java.util.HashMap;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface CommunityInterface {
    @FormUrlEncoded
    @POST("posts/share/sort")
    Single<CommonClass> get_share(@Field("sort")String sort, @Field("count")int count);

    @Multipart
    @POST("posts/share/")
    Single<CommonClass> send_newshare(@Part MultipartBody.Part image1,
                                        @Part MultipartBody.Part image2,
                                        @Part MultipartBody.Part image3,
                                        @Part MultipartBody.Part image4,
                                        @PartMap HashMap<String, RequestBody> data);
    @GET("posts/share/{id}")
    Single<CommonClass> get_share_detail(@Path("id")int id);

    @FormUrlEncoded
    @POST("posts/share/like")
    Single<CommonClass> share_like(@Field("post_to_like")int post_to_like);

    @FormUrlEncoded
    @HTTP(method="DELETE", path="posts/share/like", hasBody = true)
    Single<CommonClass> share_release_like(@Field("post_to_like")int post_to_like);

    @HTTP(method="DELETE", path="posts/share/{id}/", hasBody = true)
    Single<CommonClass> delete_share(@Path("id")int id);

    @FormUrlEncoded
    @POST("posts/share/complete")
    Single<CommonClass> share_complete(@Field("share_to_complete")int share_to_complete);

    @FormUrlEncoded
    @HTTP(method="DELETE", path="posts/share/complete", hasBody = true)
    Single<CommonClass> share_cancel_complete(@Field("share_to_complete")int share_to_complete);

    @GET("posts/store")
    Single<CommonClass> get_product();

    @FormUrlEncoded
    @POST("posts/share/search")
    Single<CommonClass> get_sigungu(@Field("sido")String sido);

    @FormUrlEncoded
    @POST("posts/share/search")
    Single<CommonClass> get_dong(@Field("sido")String sido, @Field("sigungu")String sigungu);

}
