package com.tourkakao.carping.Home.EcoDataClass;

public class EcoReview {
    private int today_count;
    private String id;
    private String user;
    private String username;
    private String image1;
    private String title;
    private String text;
    private String created_at;

    public EcoReview(){}

    public EcoReview(int today_count, String id, String user, String username, String image1, String title, String text, String created_at) {
        this.today_count = today_count;
        this.id = id;
        this.user = user;
        this.username = username;
        this.image1 = image1;
        this.title = title;
        this.text = text;
        this.created_at = created_at;
    }

    public void setToday_count(int today_count) {
        this.today_count = today_count;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getImage1() {
        return image1;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getCreated_at() {
        return created_at;
    }

    public int getToday_count() {
        return today_count;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
