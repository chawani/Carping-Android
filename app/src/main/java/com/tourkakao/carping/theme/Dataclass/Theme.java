package com.tourkakao.carping.theme.Dataclass;

import com.google.gson.annotations.SerializedName;

public class Theme {
    @SerializedName("id")
    int id;
    @SerializedName("image")
    String image;
    @SerializedName("type")
    String type;
    @SerializedName("address")
    String address;
    @SerializedName("name")
    String name;
    @SerializedName("phone")
    String phone;
    @SerializedName("distance")
    String distance;
    @SerializedName("is_bookmarked")
    boolean check_bookmark;
    @SerializedName("bookmark_count")
    int scrap_cnt;

    public Theme(int id, String image, String type, String address, String name, String phone, String distance, boolean check_bookmark, int scrap_cnt) {
        this.id = id;
        this.image = image;
        this.type = type;
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.distance = distance;
        this.check_bookmark = check_bookmark;
        this.scrap_cnt = scrap_cnt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public boolean isCheck_bookmark() {
        return check_bookmark;
    }

    public void setCheck_bookmark(boolean check_bookmark) {
        this.check_bookmark = check_bookmark;
    }

    public int getScrap_cnt() {
        return scrap_cnt;
    }

    public void setScrap_cnt(int scrap_cnt) {
        this.scrap_cnt = scrap_cnt;
    }
}
