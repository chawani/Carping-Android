package com.tourkakao.carping.Home.ThemeDataClass;

import com.google.gson.annotations.SerializedName;

public class NewCapringPlace {
    @SerializedName("id")
    int pk;
    @SerializedName("image")
    String image;

    public NewCapringPlace(int pk, String image) {
        this.pk = pk;
        this.image = image;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
