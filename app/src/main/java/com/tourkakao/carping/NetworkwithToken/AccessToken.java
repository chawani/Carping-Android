package com.tourkakao.carping.NetworkwithToken;

import com.google.gson.annotations.SerializedName;

public class AccessToken {
    @SerializedName("access_token")
    String access_token;

    public AccessToken(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
