package com.tourkakao.carping.Map.DataClass;

import com.google.gson.annotations.SerializedName;

public class MapSearchTour {
    @SerializedName("id")
    int id;
    @SerializedName("image")
    String image;
    @SerializedName("lat")
    float lat;
    @SerializedName("lon")
    float lon;
    @SerializedName("name")
    String name;
    @SerializedName("distance")
    double distance;
    @SerializedName("address")
    String address;

    public MapSearchTour(int id, String image, float lat, float lon, String name, double distance, String address) {
        this.id = id;
        this.image = image;
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.distance = distance;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public String getName() {
        return name;
    }

    public double getDistance() {
        return distance;
    }

    public String getAddress() {
        return address;
    }
}
