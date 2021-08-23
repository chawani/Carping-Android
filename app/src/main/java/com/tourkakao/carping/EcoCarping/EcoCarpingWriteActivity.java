package com.tourkakao.carping.EcoCarping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityEcoCarpingWriteBinding;

public class EcoCarpingWriteActivity extends AppCompatActivity {
    ActivityEcoCarpingWriteBinding ecobinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eco_carping_write);

        ecobinding=ActivityEcoCarpingWriteBinding.inflate(getLayoutInflater());

        settingToolbar();
    }

    public void settingToolbar(){
        Toolbar toolbar=ecobinding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

}