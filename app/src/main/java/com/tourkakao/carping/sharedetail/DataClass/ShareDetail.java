package com.tourkakao.carping.sharedetail.DataClass;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ShareDetail {
    @SerializedName("id")
    int id;
    @SerializedName("user")
    int user;
    @SerializedName("username")
    String username;
    @SerializedName("profile")
    String profile;
    @SerializedName("region")
    String region;
    @SerializedName("image1")
    String image1;
    @SerializedName("image2")
    String image2;
    @SerializedName("image3")
    String image3;
    @SerializedName("image4")
    String image4;
    @SerializedName("title")
    String title;
    @SerializedName("text")
    String text;
    @SerializedName("tags")
    ArrayList<String> tags;
    @SerializedName("chat_addr")
    String chat_addr;
    @SerializedName("created_at")
    String created_at;
    @SerializedName("like_count")
    int like_count;
    @SerializedName("is_liked")
    boolean is_liked;
    @SerializedName("is_shared")
    boolean is_shared;
    @SerializedName("comment")
    ArrayList<Comment> comment;

    public ShareDetail(int id, int user, String username, String profile, String region, String image1, String image2, String image3, String image4, String title, String text, ArrayList<String> tags, String chat_addr, String created_at, int like_count, boolean is_liked, boolean is_shared, ArrayList<Comment> comment) {
        this.id = id;
        this.user = user;
        this.username = username;
        this.profile = profile;
        this.region = region;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.title = title;
        this.text = text;
        this.tags = tags;
        this.chat_addr = chat_addr;
        this.created_at = created_at;
        this.like_count = like_count;
        this.is_liked = is_liked;
        this.is_shared = is_shared;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public int getUser() {
        return user;
    }

    public String getUsername() {
        return username;
    }

    public String getProfile() {
        return profile;
    }

    public String getRegion() {
        return region;
    }

    public String getImage1() {
        return image1;
    }

    public String getImage2() {
        return image2;
    }

    public String getImage3() {
        return image3;
    }

    public String getImage4() {
        return image4;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public String getChat_addr() {
        return chat_addr;
    }

    public String getCreated_at() {
        return created_at;
    }

    public int getLike_count() {
        return like_count;
    }

    public boolean isIs_liked() {
        return is_liked;
    }

    public boolean isIs_shared() {
        return is_shared;
    }

    public ArrayList<Comment> getComment() {
        return comment;
    }

}
