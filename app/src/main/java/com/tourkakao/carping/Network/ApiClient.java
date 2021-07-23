package com.tourkakao.carping.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL="http://chanjongp.co.kr/";

    public static ApiInterface getApiService(){
        return getInstance().create(ApiInterface.class);
    }

    private static Retrofit getInstance(){
        Gson gson=new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
