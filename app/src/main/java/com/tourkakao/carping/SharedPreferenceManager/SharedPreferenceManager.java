package com.tourkakao.carping.SharedPreferenceManager;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {
    private static SharedPreferenceManager instance=null;
    private SharedPreferences prefs;
    private SharedPreferences.Editor pref_editor;

    private SharedPreferenceManager(){}

    private SharedPreferenceManager(Context context){
        prefs=context.getSharedPreferences("carping", Context.MODE_PRIVATE);
        pref_editor=prefs.edit();
    }
    public static SharedPreferenceManager getInstance(Context context){
        if(instance==null){
            instance=new SharedPreferenceManager(context.getApplicationContext());
        }
        return instance;
    }
    public void setString(String key, String value){
        pref_editor.putString(key, value).apply();
    }
    public String getString(String key, String defValue){
        return prefs.getString(key, defValue);
    }
    public void setInt(String key, int value){
        pref_editor.putInt(key, value).apply();
    }
    public int getInt(String key, int defValue){
        return prefs.getInt(key, defValue);
    }
}
