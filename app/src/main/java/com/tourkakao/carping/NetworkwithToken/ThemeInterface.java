package com.tourkakao.carping.NetworkwithToken;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.common.internal.service.Common;
import com.tourkakao.carping.Theme.Dataclass.FilterTheme;
import com.tourkakao.carping.newcarping.DataClass.Newcarping_Review_post;

import java.util.HashMap;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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

    @Multipart
    @POST("comments/review/")
    Single<CommonClass> send_newcarping_review(@Part MultipartBody.Part image,
                                               @PartMap HashMap<String, RequestBody> text,
                                               @Part("user")int user,
                                               @Part("autocamp")int autocamp,
                                               @Part("star1")float star1,
                                               @Part("star2")float star2,
                                               @Part("star3")float star3,
                                               @Part("star4")float star4,
                                               @Part("total_star")float total_star);

    @FormUrlEncoded
    @POST("comments/review/like")
    Single<CommonClass> set_like(@Field("review_to_like")int review_to_like);

    @FormUrlEncoded
    @HTTP(method="DELETE", path="comments/review/like", hasBody = true)
    Single<CommonClass> release_like(@Field("review_to_like")int review_to_like);

    @POST("camps/theme")
    Single<CommonClass> get_thema_carping(@Body FilterTheme filterTheme);
}
