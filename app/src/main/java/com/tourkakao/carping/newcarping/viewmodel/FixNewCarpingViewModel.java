package com.tourkakao.carping.newcarping.viewmodel;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class FixNewCarpingViewModel extends ViewModel {
    public double lat, lon;
    public String f_title, f_review;
    public ArrayList<String> f_tags;
    public ArrayList<String> initial_images;
    public ArrayList<String> change_images;
    public FixNewCarpingViewModel(){
        initial_images=new ArrayList<>();
        change_images=new ArrayList<>();
    }
}
