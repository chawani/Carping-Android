package com.tourkakao.carping.Map.DataClass;

import com.google.gson.annotations.SerializedName;

public class MapSearchFromApi {
    @SerializedName("id")
    int id;
    @SerializedName("user")
    int userpk;
    @SerializedName("image1")
    String image;
    @SerializedName("latitude")
    float latitude;
    @SerializedName("longitude")
    float longitude;
    @SerializedName("title")
    String title;
    @SerializedName("text")
    String text;
    @SerializedName("distance")
    double distance;

    String address;

    public MapSearchFromApi(int id, int userpk, String image, float latitude, float longitude, String title, String text, double distance) {
        this.id = id;
        this.userpk = userpk;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.text = text;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public int getUserpk() {
        return userpk;
    }

    public String getImage() {
        return image;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public double getDistance() {
        return distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
