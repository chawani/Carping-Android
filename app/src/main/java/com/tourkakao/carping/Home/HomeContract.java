package com.tourkakao.carping.Home;

import android.Manifest;
import android.content.SharedPreferences;

public interface HomeContract {
    String[] REQUIRED_PERMISSIONS={Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    int PERMISSION_LOCATION_REQUESTCODE=1001;

    interface MainActivity_Contract{
        void initialize_sharedpreferences();
        void initialize_location_setting_class();
        void init_main_fragment();
        void switch_main_tap();
    }

    interface Location_setting_Contract{
        void setting_sharedpreferences(SharedPreferences prefs, SharedPreferences.Editor editor);
        void check_locate_permission();
    }
    interface ThemeFragment_Contract{
        void initialize_img();
        void initialize_new_carping_place_recyclerview();
        void initialize_this_weekend_recyclerview();
        void initialize_az_recyclerview();
        void initialize_popular_carping_place_recyclerview();
        void setting_this_weekend_total_btn();
        void setting_az_total_btn();
    }
}
