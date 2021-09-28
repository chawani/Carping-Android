package com.tourkakao.carping.NetworkwithToken;

import com.tourkakao.carping.Map.DataClass.MapSearch;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MapInterface {
    @GET("v2/local/search/keyword.json")
    Single<MapSearch> getSearchKeyword(
            @Header("Authorization")String key,
            @Query("query")String query,
            @Query("x")String x,
            @Query("y")String y,
            @Query("radius")int radius);

    @GET("v2/local/search/category.json")
    Single<MapSearch> getSearchCategory(
            @Header("Authorization")String key,
            @Query("category_group_code")String category_group_code,
            @Query("x")String x,
            @Query("y")String y,
            @Query("radius")int radius);

    @FormUrlEncoded
    @POST("search/map/auto-camp")
    Single<CommonClass> get_carpingmap(@Field("lat")float lat, @Field("lon")float lon);

    @FormUrlEncoded
    @POST("search/map")
    Single<CommonClass> search_tourmap(@Field("keyword")String keyword, @Field("lat")float lat, @Field("lon")float lon);
}
