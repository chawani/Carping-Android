package com.tourkakao.carping.Home.ShareDataClass;

import com.google.gson.annotations.SerializedName;

public class Share {
    @SerializedName("total_share")
    int total_share;
    @SerializedName("id")
    int pk;
    @SerializedName("image1")
    String image;
    @SerializedName("region")
    String locate;
    @SerializedName("title")
    String name;
    @SerializedName("text")
    String body;
    @SerializedName("like_count")
    int heart;
    @SerializedName("created_at")
    String time;
    @SerializedName("is_shared")
    boolean is_shared;

    public Share(int total_share, int pk, String image, String locate, String name, String body, int heart, String time, boolean is_shared) {
        this.total_share = total_share;
        this.pk = pk;
        this.image = image;
        this.locate = locate;
        this.name = name;
        this.body = body;
        this.heart = heart;
        this.time = time;
        this.is_shared = is_shared;
    }

    public int getTotal_share() {
        return total_share;
    }

    public void setTotal_share(int total_share) {
        this.total_share = total_share;
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

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isIs_shared() {
        return is_shared;
    }

    public void setIs_shared(boolean is_shared) {
        this.is_shared = is_shared;
    }
}
