package com.tourkakao.carping.Home;

import android.Manifest;
import android.content.SharedPreferences;

public interface HomeContract {
    String[] REQUIRED_PERMISSIONS={Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    int PERMISSION_LOCATION_REQUESTCODE=1001;

    interface MainActivity_Contract{
        void initialize_sharedpreferences();
        void initialize_location_setting_class();

    }

    interface Location_setting_Contract{
        void setting_sharedpreferences(SharedPreferences prefs, SharedPreferences.Editor editor);
        void check_locate_permission();
    }
}
