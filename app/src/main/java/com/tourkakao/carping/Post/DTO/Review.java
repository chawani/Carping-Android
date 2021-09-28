package com.tourkakao.carping.Post.DTO;

public class Review {
    private int id;
    private int user;
    private String username;
    private String profile;
    private String text;
    private String image;
    private float star1;
    private float star2;
    private float star3;
    private float star4;
    private float total_star;
    private String created_at;
    private int like_count;
    private boolean is_liked;

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

    public boolean isIs_liked() {
        return is_liked;
    }

    public void setIs_liked(boolean is_liked) {
        this.is_liked = is_liked;
    }
}
