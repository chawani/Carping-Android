package com.tourkakao.carping.newcarping.DataClass;

import com.google.gson.annotations.SerializedName;

public class Newcarping_Review_post {
    @SerializedName("user")
    int userpk;
    @SerializedName("autocamp")
    int autocamppk;
    @SerializedName("text")
    String review;
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

    public Newcarping_Review_post(int userpk, int autocamppk, String review, float star1, float star2, float star3, float star4, float total_star) {
        this.userpk = userpk;
        this.autocamppk = autocamppk;
        this.review = review;
        this.star1 = star1;
        this.star2 = star2;
        this.star3 = star3;
        this.star4 = star4;
        this.total_star = total_star;
    }

    public int getUserpk() {
        return userpk;
    }

    public void setUserpk(int userpk) {
        this.userpk = userpk;
    }

    public int getAutocamppk() {
        return autocamppk;
    }

    public void setAutocamppk(int autocamppk) {
        this.autocamppk = autocamppk;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
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
}
