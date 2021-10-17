package com.tourkakao.carping.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApi;
import com.kakao.sdk.user.UserApiClient;

import java.nio.file.attribute.UserPrincipal;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

import static android.content.Context.MODE_PRIVATE;

public class KakaoLogout {
    Function2<OAuthToken, Throwable, Unit> kakaologout_Callback;
    private Activity activity;

    public KakaoLogout(Context context, Activity activity){
        this.activity=activity;
    }

    public void signOut(){
        UserApiClient.getInstance().logout(error ->{
            if (error != null) {
                Toast.makeText(activity, "로그아웃에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
            else {
                finishActivities();
            }
            return null;
        });
    }

    public void revokeAccess(){
        UserApiClient.getInstance().unlink(error ->{
            if(error!=null){

            }else{
                Toast.makeText(activity, "카카오 계정 연결이 끊어졌습니다", Toast.LENGTH_SHORT).show();
            }
            return null;
        });

    }

    private void finishActivities(){
        SharedPreferences pref = activity.getSharedPreferences("carping", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }
}
