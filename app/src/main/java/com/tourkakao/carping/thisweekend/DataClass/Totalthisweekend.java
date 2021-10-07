package com.tourkakao.carping.thisweekend.DataClass;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Totalthisweekend {
    @SerializedName("id")
    int id;
    @SerializedName("created_at")
    String created_at;
    @SerializedName("updated_at")
    String updated_at;
    @SerializedName("title")
    String title;
    @SerializedName("thumbnail_with_text")
    String thumbnail;
    @SerializedName("views")
    int views;
    @SerializedName("tags")
    ArrayList<String> tags;
    @SerializedName("campsite1")
    Each_campsite campsite1;
    @SerializedName("campsite2")
    Each_campsite campsite2;
    @SerializedName("campsite3")
    Each_campsite campsite3;
    @SerializedName("count")
    int count;

    public Totalthisweekend(int id, String created_at, String updated_at, String title, String thumbnail, int views, ArrayList<String> tags, Each_campsite campsite1, Each_campsite campsite2, Each_campsite campsite3, int count) {
        this.id = id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.title = title;
        this.thumbnail = thumbnail;
        this.views = views;
        this.tags = tags;
        this.campsite1 = campsite1;
        this.campsite2 = campsite2;
        this.campsite3 = campsite3;
        this.count = count;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public Each_campsite getCampsite1() {
        return campsite1;
    }

    public void setCampsite1(Each_campsite campsite1) {
        this.campsite1 = campsite1;
    }

    public Each_campsite getCampsite2() {
        return campsite2;
    }

    public void setCampsite2(Each_campsite campsite2) {
        this.campsite2 = campsite2;
    }

    public Each_campsite getCampsite3() {
        return campsite3;
    }

    public void setCampsite3(Each_campsite campsite3) {
        this.campsite3 = campsite3;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
