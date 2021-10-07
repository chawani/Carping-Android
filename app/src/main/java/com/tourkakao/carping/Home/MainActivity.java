package com.tourkakao.carping.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.tourkakao.carping.Home.Fragment.CommunityFragment;
import com.tourkakao.carping.Home.Fragment.EcoFragment;
import com.tourkakao.carping.Home.Fragment.EcoTopFragment;
import com.tourkakao.carping.Home.Fragment.HomeFragment;
import com.tourkakao.carping.Home.Fragment.MapFragment;
import com.tourkakao.carping.Home.Fragment.MypageFragment;
import com.tourkakao.carping.Home.Fragment.ThemeFragment;
import com.tourkakao.carping.Home.Fragment.ThemeTopFragment;
import com.tourkakao.carping.Permission.Permission_setting;
import com.tourkakao.carping.R;
import com.tourkakao.carping.registernewcarping.Activity.RegisterActivity;

public class MainActivity extends AppCompatActivity{
    Permission_setting permission_setting;
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    MapFragment mapFragment;
    CommunityFragment communityFragment;
    MypageFragment mypageFragment;
    int choose=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize_permission();
        initialize_fragment();
        init_bottomnavigationview();
    }
    public void initialize_permission(){
        permission_setting=new Permission_setting(this, MainActivity.this);
        permission_setting.check_permission();
    }
    public void initialize_fragment(){
        homeFragment=new HomeFragment();
        mapFragment=new MapFragment();
        communityFragment=new CommunityFragment();
        mypageFragment=new MypageFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container, homeFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container, mapFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container, communityFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container, mypageFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(mapFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(communityFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(mypageFragment).commit();
    }
    //권한 설정 후 return
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==permission_setting.MULTIPLE_PERMISSION){
            if(grantResults.length==permission_setting.PERMISSIONS.length) {
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("권한 설정 알림")
                            .setMessage("서비스 사용을 위해서는 위치 및 갤러리 접근 권한 설정이 필요합니다.\n[설정]->[앱]에서 권한을 승인해주세요")
                            .setCancelable(false)
                            .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }
            }
        }
    }





    public void init_bottomnavigationview(){
        bottomNavigationView=findViewById(R.id.bottomnavigationview);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        choose=0;
                        getSupportFragmentManager().beginTransaction().show(homeFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(mapFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(communityFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(mypageFragment).commit();
                        break;
                    case R.id.map:
                        choose=1;
                        mapFragment.setting_map();
                        getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                        getSupportFragmentManager().beginTransaction().show(mapFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(communityFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(mypageFragment).commit();
                        break;
                    case R.id.upload:
                        choose=2;
                        getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(mapFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(communityFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(mypageFragment).commit();
                        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                        break;
                    case R.id.community:
                        choose=3;
                        getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(mapFragment).commit();
                        getSupportFragmentManager().beginTransaction().show(communityFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(mypageFragment).commit();
                        break;
                    case R.id.profile:
                        choose=4;
                        getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(mapFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(communityFragment).commit();
                        getSupportFragmentManager().beginTransaction().show(mypageFragment).commit();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapFragment.setting_remove_map();
    }

    @Override
    public void onBackPressed() {
        if(homeFragment.homebinding.slidingLayout.getPanelState()== SlidingUpPanelLayout.PanelState.COLLAPSED){
            super.onBackPressed();
        }else if(homeFragment.homebinding.slidingLayout.getPanelState()== SlidingUpPanelLayout.PanelState.EXPANDED){
            homeFragment.homebinding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch(choose){
            case 0:
            case 2:
                getSupportFragmentManager().beginTransaction().show(homeFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(mapFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(communityFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(mypageFragment).commit();
                bottomNavigationView.setSelectedItemId(R.id.home);
                break;
            case 1:
                mapFragment.setting_map();
                getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                getSupportFragmentManager().beginTransaction().show(mapFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(communityFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(mypageFragment).commit();
                bottomNavigationView.setSelectedItemId(R.id.map);
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(mapFragment).commit();
                getSupportFragmentManager().beginTransaction().show(communityFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(mypageFragment).commit();
                bottomNavigationView.setSelectedItemId(R.id.community);
                break;
            case 4:
                getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(mapFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(communityFragment).commit();
                getSupportFragmentManager().beginTransaction().show(mypageFragment).commit();
                bottomNavigationView.setSelectedItemId(R.id.profile);
                break;
        }
    }
}