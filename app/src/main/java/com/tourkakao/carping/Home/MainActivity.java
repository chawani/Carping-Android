package com.tourkakao.carping.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.tourkakao.carping.R;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements HomeContract, HomeContract.MainActivity_Contract{
    Location_setting location_setting;
    SharedPreferences main_prefs;
    SharedPreferences.Editor main_editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize_sharedpreferences();
        initialize_location_setting_class();
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
}