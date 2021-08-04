package com.tourkakao.carping.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.tourkakao.carping.Home.Fragment.EchoFragment;
import com.tourkakao.carping.Home.Fragment.EchoTopFragment;
import com.tourkakao.carping.Home.Fragment.ThemeFragment;
import com.tourkakao.carping.Home.Fragment.ThemeTopFragment;
import com.tourkakao.carping.R;

public class MainActivity extends AppCompatActivity implements HomeContract, HomeContract.MainActivity_Contract{
    Location_setting location_setting;
    SharedPreferences main_prefs;
    SharedPreferences.Editor main_editor;
    TabLayout tabs;
    EchoTopFragment echo_top_fragment;
    EchoFragment echo_fragment;
    ThemeTopFragment theme_top_fragment;
    ThemeFragment theme_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize_sharedpreferences();
        initialize_location_setting_class();

        init_main_fragment();
        switch_main_tap();
    }

    @Override
    public void initialize_sharedpreferences() {
        main_prefs=getSharedPreferences("carping", Activity.MODE_PRIVATE);
        main_editor=main_prefs.edit();
    }
    @Override
    public void initialize_location_setting_class() {
        location_setting=new Location_setting(this, MainActivity.this);
        location_setting.setting_sharedpreferences(main_prefs, main_editor);
        location_setting.check_locate_permission();
    }

    //권한 설정 후 return
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==PERMISSION_LOCATION_REQUESTCODE){
            if(grantResults.length==REQUIRED_PERMISSIONS.length){
                boolean check_result=true;
                for(int result: grantResults){
                    if(result== PackageManager.PERMISSION_DENIED){
                        check_result=false;
                        break;
                    }
                }
                if(check_result){
                    Toast.makeText(MainActivity.this, "위치 권한이 설정되었습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("위치 권한 설정 알림")
                            .setMessage("서비스 사용을 위해서는 위치 권한 설정이 필요합니다. [설정]->[앱]에서 위치 권한을 승인해주세요")
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
        echo_top_fragment=new EchoTopFragment();
        echo_fragment = new EchoFragment();
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
                    top_selected = echo_top_fragment;
                    selected = echo_fragment;
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