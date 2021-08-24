package com.tourkakao.carping.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.tourkakao.carping.Gallerypermission.Gallery_setting;
import com.tourkakao.carping.Home.Fragment.EcoFragment;
import com.tourkakao.carping.Home.Fragment.EcoTopFragment;
import com.tourkakao.carping.Home.Fragment.ThemeFragment;
import com.tourkakao.carping.Home.Fragment.ThemeTopFragment;
import com.tourkakao.carping.Location_setting.Location_setting;
import com.tourkakao.carping.Permission.Permission_setting;
import com.tourkakao.carping.R;

public class MainActivity extends AppCompatActivity implements HomeContract, HomeContract.MainActivity_Contract{
    Permission_setting permission_setting;
    TabLayout tabs;
    EcoTopFragment eco_top_fragment;
    EcoFragment eco_fragment;
    ThemeTopFragment theme_top_fragment;
    ThemeFragment theme_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize_permission();

        init_main_fragment();
        switch_main_tap();
    }
    public void initialize_permission(){
        permission_setting=new Permission_setting(this, MainActivity.this);
        permission_setting.check_permission();
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

    @Override
    public void init_main_fragment(){
        eco_top_fragment =new EcoTopFragment();
        eco_fragment = new EcoFragment();
        theme_top_fragment=new ThemeTopFragment();
        theme_fragment = new ThemeFragment();
    }

    @Override
    public void switch_main_tap(){
        getSupportFragmentManager().beginTransaction().add(R.id.top_container, theme_top_fragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container, theme_fragment).commit();

        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("테마카핑"));
        tabs.addTab(tabs.newTab().setText("에코카핑"));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment top_selected=null;
                Fragment selected = null;
                if(position == 0) {
                    top_selected = theme_top_fragment;
                    selected = theme_fragment;
                }
                else if(position == 1) {
                    top_selected = eco_top_fragment;
                    selected = eco_fragment;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.top_container, top_selected).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}