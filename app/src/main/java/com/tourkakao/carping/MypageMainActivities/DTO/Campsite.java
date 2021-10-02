package com.tourkakao.carping.MypageMainActivities.DTO;

public class Campsite{
    int id;
    String image;
    String name;
    String address;
    float distance;
    int bookmark_count;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getBookmark_count() {
        return bookmark_count;
    }

    public void setBookmark_count(int bookmark_count) {
        this.bookmark_count = bookmark_count;
    }
}