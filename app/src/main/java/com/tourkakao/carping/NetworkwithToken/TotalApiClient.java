package com.tourkakao.carping.NetworkwithToken;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tourkakao.carping.Login.LoginActivity;

import org.apache.http.HttpStatus;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TotalApiClient {
    private static final String BASE_URL="http://chanjongp.co.kr/";

    private static Retrofit getInstance(Context context){
        Retrofit retrofit=null;
        Gson gson=new GsonBuilder().setLenient().create();
        ResponseCodeCheckInterceptor interceptor=new ResponseCodeCheckInterceptor(context);
        OkHttpClient client=new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}

class ResponseCodeCheckInterceptor implements Interceptor{
    private Context context;

    public ResponseCodeCheckInterceptor(Context context){
        this.context=context;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        Request authenticatedRequest=request.newBuilder()
                .header("Authorization", "mytoken").build();
        Response response=chain.proceed(authenticatedRequest);
        if(response.code()== HttpStatus.SC_UNAUTHORIZED){
            /*Request modifiedRequest=request.newBuilder()
                    .header("Authorization", getNewToken()).build();
            response=chain.proceed(modifiedRequest);*/
            response=null;
            context.startActivity(new Intent(context, LoginActivity.class));
        }else if(response.code()==HttpStatus.SC_FORBIDDEN){
            response=null;
            context.startActivity(new Intent(context, LoginActivity.class));
        }
        return response;
    }
}
