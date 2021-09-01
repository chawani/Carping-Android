package com.tourkakao.carping.Login;

import com.google.gson.annotations.SerializedName;

public class Google_User_Info {
    @SerializedName("pk")
    private final int pk;
    @SerializedName("username")
    private final String username;
    @SerializedName("email")
    private final String email;
    @SerializedName("profile")
    private final Google_User_Profile profile;

    public Google_User_Info(int pk, String username, String email, Google_User_Profile profile) {
        this.pk = pk;
        this.username = username;
        this.email = email;
        this.profile = profile;
    }

    public int getPk() {
        return pk;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Google_User_Profile getProfile() {
        return profile;
    }
}

