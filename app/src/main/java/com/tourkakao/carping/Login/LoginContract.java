package com.tourkakao.carping.Login;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

public interface LoginContract {
    interface Kakaologin{
        void Login();
        void setting_kakaologin_callback();
    }

    interface GoogleLogin{
        void signIn();
        void handleSignInResult(Task<GoogleSignInAccount> completedTask);
    }
}
