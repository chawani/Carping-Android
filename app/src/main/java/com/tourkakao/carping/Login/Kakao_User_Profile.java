package com.tourkakao.carping.Login;

import com.google.gson.annotations.SerializedName;

public class Kakao_User_Profile {
    @SerializedName("image")
    String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
