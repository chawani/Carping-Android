package com.tourkakao.carping.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.tourkakao.carping.BuildConfig;

import static android.content.Context.MODE_PRIVATE;

public class GoogleLogout {
    private final String CLIENT_ID;
    private final String CLIENT_SECRET;
    private GoogleSignInClient mGoogleSignInClient;
    private Activity activity;

    public  GoogleLogout(Context context, Activity activity){
        CLIENT_ID = BuildConfig.GOOGLE_CLIENT_ID;
        CLIENT_SECRET=BuildConfig.GOOGLE_CLIENT_SECRET;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(CLIENT_ID)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        this.activity=activity;
    }

    //로그아웃
    public void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        revokeAccess();
                        finishActivities();
                    }
                });
    }

    //계정 연결 해제
    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
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
