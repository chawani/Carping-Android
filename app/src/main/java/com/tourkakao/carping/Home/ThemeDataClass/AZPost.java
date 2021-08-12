package com.tourkakao.carping.Home.ThemeDataClass;

import com.google.gson.annotations.SerializedName;

public class AZPost {
    @SerializedName("")
    int pk;
    @SerializedName("")
    String title;
    @SerializedName("")
    String image;
    @SerializedName("")
    String profile;
    @SerializedName("")
    int star_score;
    @SerializedName("")
    int star_number;
    @SerializedName("")
    int isprimeum;

    public AZPost(int pk, String title, String image, String profile, int star_score, int star_number, int isprimeum) {
        this.pk = pk;
        this.title = title;
        this.image = image;
        this.profile = profile;
        this.star_score = star_score;
        this.star_number = star_number;
        this.isprimeum = isprimeum;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getStar_score() {
        return star_score;
    }

    public void setStar_score(int star_score) {
        this.star_score = star_score;
    }

    public int getStar_number() {
        return star_number;
    }

    public void setStar_number(int star_number) {
        this.star_number = star_number;
    }

    public int getIsprimeum() {
        return isprimeum;
    }

    public void setIsprimeum(int isprimeum) {
        this.isprimeum = isprimeum;
    }
}
