package com.tourkakao.carping.Home;

import android.Manifest;
import android.content.SharedPreferences;

public interface HomeContract {
    String[] REQUIRED_PERMISSIONS={Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    int PERMISSION_LOCATION_REQUESTCODE=1001;

    interface MainActivity_Contract{
        void initialize_location_setting_class();
        void init_main_fragment();
        void switch_main_tap();
    }

    interface Location_setting_Contract{
        void check_locate_permission();
    }
    interface ThemeFragment_Contract{
        void initialize_img();
        void setting_this_weekend_total_btn();
        void setting_az_total_btn();
    }
}
