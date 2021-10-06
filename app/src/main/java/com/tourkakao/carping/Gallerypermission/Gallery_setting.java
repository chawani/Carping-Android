package com.tourkakao.carping.Gallerypermission;

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

public class Gallery_setting {
    Context context;
    Activity used_Activity;
    public String[] REQUIRED_PERMISSIONS={Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public int PERMISSION_GALLERY_REQUESTCODE=1002;
    public int GALLERY_CODE=102;

    public Gallery_setting(Context context, Activity used_Activity){
        this.context=context;
        this.used_Activity=used_Activity;
    }

    public void check_gallery_permission(){
        if(Build.VERSION.SDK_INT>=23){
            int permission_read=context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int permission_write=context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permission_read== PackageManager.PERMISSION_DENIED || permission_write==PackageManager.PERMISSION_DENIED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(used_Activity, REQUIRED_PERMISSIONS[0]) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(used_Activity, REQUIRED_PERMISSIONS[1])) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setTitle("갤러리 접근 권한 설정 알림")
                            .setMessage("서비스 사용을 위해서는 갤러리 접근 권한 설정이 필요합니다.")
                            .setCancelable(false)
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(used_Activity, REQUIRED_PERMISSIONS, PERMISSION_GALLERY_REQUESTCODE);
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setTitle("갤러리 권한 설정 알림")
                            .setMessage("서비스 사용을 위해서는 갤러리 접근 권한 설정이 필요합니다.\n설정 화면으로 이동합니다.")
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
