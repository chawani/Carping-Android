package com.tourkakao.carping.NetworkwithToken;

import com.tourkakao.carping.theme.Dataclass.DaumBlog;
import com.tourkakao.carping.theme.Dataclass.FilterTheme;
import com.tourkakao.carping.theme.Dataclass.TourSearch;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @FormUrlEncoded
    @POST("search/popular-site")
    Single<CommonClass> get_popular_place(@Field("region")String region);

    @GET("camps/auto-camp/{pk}")
    Single<CommonClass> get_each_newcarping_place_detail(@Path("pk")int pk);

    @FormUrlEncoded
    @POST("camps/auto-camp/bookmark")
    Single<CommonClass> set_bookmark(@Field("autocamp_to_bookmark")int autocamp_to_bookmark);

    @FormUrlEncoded
    @HTTP(method="DELETE", path="camps/auto-camp/bookmark", hasBody = true)
    Single<CommonClass> release_bookmark(@Field("autocamp_to_bookmark")int autocamp_to_bookmark);

    @HTTP(method="DELETE", path="camps/auto-camp/{id}/", hasBody = true)
    Single<CommonClass> delete_newcarping(@Path("id")int id);

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

    @FormUrlEncoded
    @POST("camps/theme/bookmark")
    Single<CommonClass> set_theme_bookmark(@Field("campsite_to_bookmark")int campsite_to_bookmark);

    @FormUrlEncoded
    @HTTP(method="DELETE", path="camps/theme/bookmark", hasBody=true)
    Single<CommonClass> release_theme_bookmark(@Field("campsite_to_bookmark")int campsite_to_bookmark);

    @Multipart
    @PATCH("comments/review/{id}/")
    Single<CommonClass> change_newcarping_review(@Path("id")int id,
                                                 @Part MultipartBody.Part image,
                                                 @PartMap HashMap<String, RequestBody> text,
                                                 @Part("user")int user,
                                                 @Part("autocamp")int autocamp,
                                                 @Part("star1")float star1,
                                                 @Part("star2")float star2,
                                                 @Part("star3")float star3,
                                                 @Part("star4")float star4,
                                                 @Part("total_star")float total_star);
    @Multipart
    @PATCH("comments/review/{id}/")
    Single<CommonClass> change_newcarping_review_no_image(@Path("id")int id,
                                                 @PartMap HashMap<String, RequestBody> text,
                                                 @Part("user")int user,
                                                 @Part("autocamp")int autocamp,
                                                 @Part("star1")float star1,
                                                 @Part("star2")float star2,
                                                 @Part("star3")float star3,
                                                 @Part("star4")float star4,
                                                 @Part("total_star")float total_star);

    @HTTP(method="DELETE", path="comments/review/{id}/", hasBody=true)
    Single<CommonClass> delete_newcarping_review(@Path("id")int id);


    @GET("v2/search/blog")
    Single<DaumBlog> getblog(@Header("Authorization")String key, @Query("query")String query);

    @FormUrlEncoded
    @POST("camps/theme/detail/{pk}")
    Single<CommonClass> get_themedetail(@Path("pk")int pk, @Field("lat")double lat, @Field("lon")double lon);

    @Multipart
    @PATCH("camps/auto-camp/{id}/")
    Single<CommonClass> edit_newcarping(@Path("id")int id,
                                        @Part MultipartBody.Part image1,
                                        @Part MultipartBody.Part image2,
                                        @Part MultipartBody.Part image3,
                                        @Part MultipartBody.Part image4,
                                        @PartMap HashMap<String, RequestBody> data,
                                        @Part("latitude")double latitude,
                                        @Part("longitude")double longitude,
                                        @Part("is_null")ArrayList<Integer> is_null);

    @FormUrlEncoded
    @POST("search/region")
    Single<CommonClass> get_eachcity(@Field("region")String region);

    @FormUrlEncoded
    @POST("search/main")
    Single<CommonClass> main_search(@Field("keyword")String keyword, @Field("lat")double lat, @Field("lon")double lon);

    @FormUrlEncoded
    @POST("search/complete")
    Single<CommonClass> save_search_keyword(@Field("keyword")String keyword, @Field("name")String name, @Field("type")String type);

    @FormUrlEncoded
    @POST("search/keyword")
    Single<CommonClass> get_popular_recent_keyword(@Field("type")String type);

    @FormUrlEncoded
    @HTTP(method="DELETE", path="search/keyword", hasBody = true)
    Single<CommonClass> delete_search_keyword(@Field("keyword")String keyword, @Field("type")String type);

    @GET("v2/local/search/category.json")
    Single<TourSearch> getTour(
            @Header("Authorization")String key,
            @Query("category_group_code")String category_group_code,
            @Query("x")String x,
            @Query("y")String y,
            @Query("radius")int radius);

    @GET("v2/local/search/category.json")
    Single<TourSearch> getRestaurant(
            @Header("Authorization")String key,
            @Query("category_group_code")String category_group_code,
            @Query("x")String x,
            @Query("y")String y,
            @Query("radius")int radius);

    @GET("v2/local/search/category.json")
    Single<TourSearch> getCulture(
            @Header("Authorization")String key,
            @Query("category_group_code")String category_group_code,
            @Query("x")String x,
            @Query("y")String y,
            @Query("radius")int radius);

}
