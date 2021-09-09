package com.tourkakao.carping.MypageMainActivities.DTO;

public class Campsite{
    int id;
    String image;
    String name;
    String address;
    String distance;
    String bookmark_count;

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDistance() {
        return distance;
    }

    public String  getBookmark_count() {
        return bookmark_count;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setBookmark_count(String bookmark_count) {
        this.bookmark_count = bookmark_count;
    }
}