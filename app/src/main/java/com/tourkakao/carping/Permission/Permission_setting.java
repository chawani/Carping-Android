package com.tourkakao.carping.Permission;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;

public class Permission_setting {
    Context context;
    Activity used_Activity;
    boolean isFirstCheck;
    public String[] PERMISSIONS={
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public int MULTIPLE_PERMISSION=1001;

    public Permission_setting(Context context, Activity used_Activity){
        this.context=context;
        this.used_Activity=used_Activity;
        this.isFirstCheck= SharedPreferenceManager.getInstance(context).getBoolean("isFirstCheck", true);
    }

    public void check_permission(){
        boolean granted=true;
        if(Build.VERSION.SDK_INT>=23){
            for(String permission:PERMISSIONS){
                if(context.checkSelfPermission(permission)== PackageManager.PERMISSION_DENIED){
                    granted=false;
                }
            }
            if(!granted){
                if(ActivityCompat.shouldShowRequestPermissionRationale(used_Activity, PERMISSIONS[0]) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(used_Activity, PERMISSIONS[1]) ||
                ActivityCompat.shouldShowRequestPermissionRationale(used_Activity, PERMISSIONS[2])||
                ActivityCompat.shouldShowRequestPermissionRationale(used_Activity, PERMISSIONS[3])){
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setTitle("권한 설정 알림")
                            .setMessage("서비스 사용을 위해서는 위치 및 갤러리 접근 권한 설정이 필요합니다.")
                            .setCancelable(false)
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(used_Activity, PERMISSIONS, MULTIPLE_PERMISSION);
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }else{
                    if(isFirstCheck){//사용자에게 처음 물어본 경우(처음 앱을 실행한 경우)
                        SharedPreferenceManager.getInstance(context).setBoolean("isFirstPermissionCheck", false);
                        ActivityCompat.requestPermissions(used_Activity, PERMISSIONS, MULTIPLE_PERMISSION);
                    }else{//권한 설정 거부와 동시에 다시 묻지않음을 선택한 경우, 이번만 허용한 경우, 누적 두번 이상 거절한 경우
                        AlertDialog.Builder builder=new AlertDialog.Builder(context);
                        builder.setTitle("권한 설정 알림")
                                .setMessage("서비스 사용을 위해서는 위치 및 갤러리 접근 권한 설정이 필요합니다. 설정 화면으로 이동합니다.")
                                .setCancelable(false)
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent setting_permission_intent=new Intent();
                                        //핸드폰 어플리케이션 설정으로 이동
                                        setting_permission_intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        setting_permission_intent.setData(Uri.parse("package:"+"com.tourkakao.carping"));
                                        context.startActivity(setting_permission_intent);
                                    }
                                });
                        builder.create().show();
                    }
                }
            }
        }
    }
}
