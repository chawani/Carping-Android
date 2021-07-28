package com.tourkakao.carping.Login;

import com.google.gson.annotations.SerializedName;

public class Google_Access_Token {
    @SerializedName("access_token")
    private final String access_token;
    @SerializedName("expires_in")
    private final int expires_in;
    @SerializedName("refresh_token")
    private final String refresh_token;
    @SerializedName("scope")
    private final String scope;
    @SerializedName("token_type")
    private final String token_type;

    public Google_Access_Token(String access_token, int expires_in, String refresh_token, String scope, String token_type) {
        this.access_token=access_token;
        this.expires_in=expires_in;
        this.refresh_token=refresh_token;
        this.scope=scope;
        this.token_type=token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public String getToken_type() {
        return token_type;
    }

    @Override
    public String toString() {
        return "Google_Access_Token{" +
                "access_token='" + access_token + '\'' +
                ", expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\'' +
                ", scope='" + scope + '\'' +
                ", token_type='" + token_type + '\'' +
                '}';
    }
}
