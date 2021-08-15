package com.tourkakao.carping.Thisweekend.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityEachThisWeekendBinding;

public class Each_ThisWeekendActivity extends AppCompatActivity {
    private ActivityEachThisWeekendBinding eachThisWeekendBinding;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eachThisWeekendBinding=ActivityEachThisWeekendBinding.inflate(getLayoutInflater());
        setContentView(eachThisWeekendBinding.getRoot());
        context=getApplicationContext();
    }
}