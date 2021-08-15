package com.tourkakao.carping.Home.ThemeDataClass;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Thisweekend {
    @SerializedName("id")
    int pk;
    @SerializedName("thumbnail")
    String image;
    @SerializedName("title")
    String title;
    @SerializedName("tags")
    ArrayList<String> tags;
    @SerializedName("views")
    int views;

    public Thisweekend(int pk, String image, String title, ArrayList<String> tags, int views) {
        this.pk = pk;
        this.image = image;
        this.title = title;
        this.tags = tags;
        this.views=views;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
