package com.tourkakao.carping.Login;

import com.google.gson.annotations.SerializedName;

public class Kakao_Token_and_User_Info {
    @SerializedName("access_token")
    String access_token;
    @SerializedName("refresh_token")
    String refresh_token;
    @SerializedName("user")
    Kakao_User_Info user;

    public Kakao_Token_and_User_Info() {
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public Kakao_User_Info getUser() {
        return user;
    }

    public void setUser(Kakao_User_Info user) {
        this.user = user;
    }
}
