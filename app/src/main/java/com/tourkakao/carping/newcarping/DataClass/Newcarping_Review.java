package com.tourkakao.carping.newcarping.DataClass;

import com.google.gson.annotations.SerializedName;

public class Newcarping_Review {
    @SerializedName("id")
    int id;
    @SerializedName("user")
    int user;
    @SerializedName("username")
    String username;
    @SerializedName("")
    String review_profile;
    @SerializedName("text")
    String text;
    @SerializedName("image")
    String image;
    @SerializedName("star1")
    float star1;
    @SerializedName("star2")
    float star2;
    @SerializedName("star3")
    float star3;
    @SerializedName("star4")
    float star4;
    @SerializedName("total_star")
    float total_star;
    @SerializedName("created_at")
    String created_at;
    @SerializedName("like_count")
    int like_count;
    @SerializedName("check_like")
    int check_like;

    public Newcarping_Review(int id, int user, String username, String review_profile, String text, String image, float star1, float star2, float star3, float star4, float total_star, String created_at, int like_count, int check_like) {
        this.id = id;
        this.user = user;
        this.username = username;
        this.review_profile = review_profile;
        this.text = text;
        this.image = image;
        this.star1 = star1;
        this.star2 = star2;
        this.star3 = star3;
        this.star4 = star4;
        this.total_star = total_star;
        this.created_at = created_at;
        this.like_count = like_count;
        this.check_like = check_like;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReview_profile() {
        return review_profile;
    }

    public void setReview_profile(String review_profile) {
        this.review_profile = review_profile;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getStar1() {
        return star1;
    }

    public void setStar1(float star1) {
        this.star1 = star1;
    }

    public float getStar2() {
        return star2;
    }

    public void setStar2(float star2) {
        this.star2 = star2;
    }

    public float getStar3() {
        return star3;
    }

    public void setStar3(float star3) {
        this.star3 = star3;
    }

    public float getStar4() {
        return star4;
    }

    public void setStar4(float star4) {
        this.star4 = star4;
    }

    public float getTotal_star() {
        return total_star;
    }

    public void setTotal_star(float total_star) {
        this.total_star = total_star;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getCheck_like() {
        return check_like;
    }

    public void setCheck_like(int check_like) {
        this.check_like = check_like;
    }
}
