package com.tourkakao.carping.Home.ThemeDataClass;

import com.google.gson.annotations.SerializedName;

public class AZPost {
    private String user_profile;
    private String id;
    private String title;
    private String total_star_avg;
    private String author;
    private String thumbnail;
    private String is_liked;
    private String category;
    private String pay_type;
    private String point;

    public String getUser_profile() {
        return user_profile;
    }

    public void setUser_profile(String user_profile) {
        this.user_profile = user_profile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotal_star_avg() {
        return total_star_avg;
    }

    public void setTotal_star_avg(String total_star_avg) {
        this.total_star_avg = total_star_avg;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getIs_liked() {
        return is_liked;
    }

    public void setIs_liked(String is_liked) {
        this.is_liked = is_liked;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
