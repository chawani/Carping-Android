package com.tourkakao.carping.Login;

import com.google.gson.annotations.SerializedName;

public class Kakao_User_Info {
    @SerializedName("pk")
    int pk;
    @SerializedName("username")
    String username;
    @SerializedName("email")
    String email;
    @SerializedName("profile")
    Kakao_User_Profile profile;

    public Kakao_User_Info() {
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getUserkname() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Kakao_User_Profile getProfile() {
        return profile;
    }

    public void setProfile(Kakao_User_Profile profile) {
        this.profile = profile;
    }
}
