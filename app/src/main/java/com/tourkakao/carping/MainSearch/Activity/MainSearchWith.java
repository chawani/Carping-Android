package com.tourkakao.carping.MainSearch.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.GpsLocation.GpsTracker;
import com.tourkakao.carping.Home.MainActivity;
import com.tourkakao.carping.Location_setting.Location_setting;
import com.tourkakao.carping.MainSearch.Adapter.MainSearchAdapter;
import com.tourkakao.carping.MainSearch.Adapter.RecentAdapter;
import com.tourkakao.carping.MainSearch.ViewModel.MainSearchViewModel;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityMainSearchWithBinding;
import com.tourkakao.carping.theme.Activity.ThemeDetailActivity;

import java.util.ArrayList;


public class MainSearchWith extends AppCompatActivity {
    ActivityMainSearchWithBinding searchWithBinding;
    Context context;
    MainSearchViewModel mainSearchViewModel;
    RecentAdapter recentAdapter;
    MainSearchAdapter mainSearchAdapter;
    String keyword="";
    double mylat, mylon;
    Location_setting location_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchWithBinding = ActivityMainSearchWithBinding.inflate(getLayoutInflater());
        setContentView(searchWithBinding.getRoot());
        context = this;

        Glide.with(context).load(R.drawable.back).into(searchWithBinding.back);
        Glide.with(context).load(R.drawable.search_remove_img).into(searchWithBinding.remove);

        mainSearchViewModel=new ViewModelProvider(this).get(MainSearchViewModel.class);
        mainSearchViewModel.setContext(context);

        checking_user_locate_setting();
        getting_mylocate();
        starting_observe_popular();
        setting_back_btn();
        setting_recent_recyclerview();
        setting_search_recyclerview();
        setting_search();
    }
    public void setting_back_btn(){
        searchWithBinding.back.setOnClickListener(v -> {
            finish();
        });
    }
    public void checking_user_locate_setting(){
        LocationManager locationManager=(LocationManager)context.getSystemService(LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("위치 서비스 사용")
                    .setMessage("서비스 사용을 위해서는 핸드폰 위치 서비스를 활성화해야 합니다. 설정으로 이동하겠습니다.")
                    .setCancelable(false)
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            finish();
                        }
                    }).create().show();
        }else{
            checking_locate_permission();
        }
    }
    public void checking_locate_permission(){
        location_setting=new Location_setting(context, MainSearchWith.this);
        if(Build.VERSION.SDK_INT>=23){
            int permission_fine=context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            int permission_coarse=context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            if(permission_fine== PackageManager.PERMISSION_DENIED || permission_coarse==PackageManager.PERMISSION_DENIED){
                location_setting.check_locate_permission();
            }
        }
    }
    public void getting_mylocate(){
        GpsTracker gpsTracker=new GpsTracker(context);
        gpsTracker.getLocation();
        mylat=gpsTracker.getLatitude();
        mylon=gpsTracker.getLongitude();
        gpsTracker.stopUsingGPS();
    }
    public void starting_observe_popular(){
        mainSearchViewModel.populars.observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                searchWithBinding.popularContainer.removeAllViews();
                for (int i = 0; i < strings.size(); i++) {
                    TextView popular = new TextView(context);
                    popular.setText(strings.get(i));
                    popular.setBackgroundResource(R.drawable.purple_border_round);
                    popular.setPadding(60, 30, 60, 30);
                    popular.setTextColor(Color.parseColor("#5f51ef"));
                    LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.rightMargin=25;
                    popular.setLayoutParams(params);
                    popular.setClickable(true);
                    int finalI = i;
                    popular.setOnClickListener(v -> {
                        keyword=strings.get(finalI);
                        searchWithBinding.mainSearch.setText(keyword);
                        mainSearchViewModel.searching(keyword, mylat, mylon);
                    });
                    searchWithBinding.popularContainer.addView(popular);
                }
            }
        });
    }
    public void setting_recent_recyclerview(){
        searchWithBinding.recentRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        recentAdapter=mainSearchViewModel.setting_recentadapter();
        searchWithBinding.recentRecyclerview.setAdapter(recentAdapter);
        recentAdapter.setOnSelectItemCLickListener(new RecentAdapter.OnSelectItemClickListener() {
            @Override
            public void OnSelectItemClick(View v, int pos, String word) {
                keyword=word;
                searchWithBinding.mainSearch.setText(keyword);
                mainSearchViewModel.searching(keyword, mylat, mylon);
            }
        });
    }
    public void setting_search(){
        searchWithBinding.mainSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getting_mylocate();
                keyword=s.toString();
                if(keyword.length()==0){
                    searchWithBinding.mainSearchRecyclerview.setVisibility(View.GONE);
                }else {
                    searchWithBinding.mainSearchRecyclerview.setVisibility(View.VISIBLE);
                    mainSearchViewModel.searching(keyword, mylat, mylon);
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        searchWithBinding.remove.setOnClickListener(v -> {
            keyword="";
            searchWithBinding.mainSearch.setText("");
        });
    }
    public void setting_search_recyclerview(){
        searchWithBinding.mainSearchRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        mainSearchAdapter=mainSearchViewModel.setting_mainadapter();
        searchWithBinding.mainSearchRecyclerview.setAdapter(mainSearchAdapter);
        mainSearchAdapter.setOnSelectItemCLickListener(new MainSearchAdapter.OnSelectItemClickListener() {
            @Override
            public void OnSelectItemClick(View v, int pos, int pk) {
                String name=mainSearchViewModel.searches.get(pos).getName();
                mainSearchViewModel.search_complete(keyword, name);
                Intent intent=new Intent(context, ThemeDetailActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("pk", pk);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length==2) {
            boolean check_result = true;
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    check_result = false;
                    break;
                }
            }
            if (check_result) {
                Toast.makeText(this, "권한이 설정되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainSearchWith.this);
                builder.setTitle("위치 권한 설정 알림")
                        .setMessage("서비스 사용을 위해서는 위치 접근 권한 설정이 필요합니다.\n[설정]->[앱]에서 권한을 승인해주세요")
                        .setCancelable(false)
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                builder.create().show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainSearchViewModel.get_popular_and_recent();
    }
}