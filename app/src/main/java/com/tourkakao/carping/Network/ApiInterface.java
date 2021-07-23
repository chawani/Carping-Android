package com.tourkakao.carping.Network;

import com.tourkakao.carping.Login.Kakao_User_Info;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("accounts/kakao/login/")
    Call<Kakao_User_Info> kakao_signin(@Field("access_token")String access_token);
}