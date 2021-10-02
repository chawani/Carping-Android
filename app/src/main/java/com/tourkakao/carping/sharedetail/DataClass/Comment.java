package com.tourkakao.carping.sharedetail.DataClass;

import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("id")
    int id;
    @SerializedName("user")
    int user;
    @SerializedName("username")
    String username;
    @SerializedName("profile")
    String profile;
    @SerializedName("level")
    String level;
    @SerializedName("badge")
    String badge;
    @SerializedName("text")
    String text;
    @SerializedName("root")
    String root;
    @SerializedName("created_at")
    String created_at;

    public Comment(int id, int user, String username, String profile, String level, String badge, String text, String root, String created_at) {
        this.id = id;
        this.user = user;
        this.username = username;
        this.profile = profile;
        this.level = level;
        this.badge = badge;
        this.text = text;
        this.root = root;
        this.created_at = created_at;
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

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
