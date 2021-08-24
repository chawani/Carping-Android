package com.tourkakao.carping.EcoCarping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityEcoCarpingWriteBinding;

public class EcoCarpingWriteActivity extends AppCompatActivity {
    ActivityEcoCarpingWriteBinding ecobinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ecobinding=ActivityEcoCarpingWriteBinding.inflate(getLayoutInflater());
        setContentView(ecobinding.getRoot());

        settingToolbar();

        ecobinding.searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_SEARCH) {
                    System.out.println("눌렀음");
                    Intent it = new Intent(getApplicationContext(), LocationSearchActivity.class);
                    it.putExtra("검색어", ecobinding.searchBar.getText().toString());
                    startActivity(it);
                    return true;
                }
                System.out.println("안눌렀음");
                return false;
            }
        });

    }

    public void settingToolbar(){
        Toolbar toolbar=ecobinding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

}