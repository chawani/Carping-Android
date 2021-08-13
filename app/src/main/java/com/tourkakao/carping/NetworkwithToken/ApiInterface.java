package com.tourkakao.carping.NetworkwithToken;

import com.tourkakao.carping.Home.ThemeDataClass.AZPost;
import com.tourkakao.carping.Home.ThemeDataClass.Thisweekend;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("")
    Single<ArrayList<Thisweekend>> get_thisweekend_post();

    @GET("")
    Single<ArrayList<AZPost>> get_az_post();
}
