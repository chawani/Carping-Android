package com.tourkakao.carping.Login;

import com.google.gson.annotations.SerializedName;

public class Google_User_Profile {
    @SerializedName("image")
    private String image;

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
