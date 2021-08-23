package com.tourkakao.carping.NetworkwithToken;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;

import org.apache.http.HttpStatus;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TotalApiClient {
    private static final String BASE_URL="http://chanjongp.co.kr/";

    public static ThemeInterface getApiService(Context context){
        return getInstance(context).create(ThemeInterface.class);
    }

    public static EcoInterface getEcoApiService(Context context){
        return getInstance(context).create(EcoInterface.class);
    }

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
                .header("Authorization", "Bearer "+SharedPreferenceManager.getInstance(context).getString("access_token", null)).build();
        System.out.println(request);
        Response response=chain.proceed(authenticatedRequest);
        System.out.println(response);
        if(response.code()== HttpStatus.SC_UNAUTHORIZED){
            response.close();
            getNewToken();
            Request modifiedRequest=request.newBuilder()
                    .header("Authorization", "Bearer "+SharedPreferenceManager.getInstance(context).getString("access_token", null)).build();
            response=chain.proceed(modifiedRequest);
        }
        return response;
    }

    public void getNewToken(){
        TotalApiClient.getApiService(context).getNewtoken(SharedPreferenceManager.getInstance(context).getString("refresh_token", null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        access_token -> {
                            System.out.println("getnewtoken success");
                            SharedPreferenceManager.getInstance(context).setString("access_token", access_token.access_token);
                        },
                        error -> {
                        });
    }
}
