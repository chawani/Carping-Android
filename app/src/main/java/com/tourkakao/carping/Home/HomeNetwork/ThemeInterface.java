package com.tourkakao.carping.Home.HomeNetwork;

import com.tourkakao.carping.Home.ThemeDataClass.AZPost;
import com.tourkakao.carping.Home.ThemeDataClass.Thisweekend;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ThemeInterface {
    @GET("")
    Single<ArrayList<Thisweekend>> get_thisweekend_post(@Header("") String header);

    @GET("")
    Single<ArrayList<AZPost>> get_az_post(@Header("") String header);
}
