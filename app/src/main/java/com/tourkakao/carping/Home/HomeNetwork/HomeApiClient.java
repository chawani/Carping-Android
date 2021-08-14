package com.tourkakao.carping.Home.HomeNetwork;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeApiClient {
    private static final String BASE_URL="http://chanjongp.co.kr/";

    public static EchoInterface getEchoApiService(){
        return getInstance().create(EchoInterface.class);
    }


    private static Retrofit getInstance(){
        Gson gson=new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

}
