package com.tourkakao.carping.Login;

import com.google.gson.annotations.SerializedName;

public class Google_Token_and_User_Info {
    @SerializedName("access_token")
    private final String access_token;
    @SerializedName("refresh_token")
    private final String refresh_token;
    @SerializedName("user")
    private final Google_User_Info user;

    public Google_Token_and_User_Info(String access_token, String refresh_token, Google_User_Info user) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.user = user;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public Google_User_Info getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Google_Token_and_User_Info{" +
                "access_token='" + access_token + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", user=" + user +
                '}';
    }
}
