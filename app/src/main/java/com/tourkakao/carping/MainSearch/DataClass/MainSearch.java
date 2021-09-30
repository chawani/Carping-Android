package com.tourkakao.carping.MainSearch.DataClass;

import com.google.gson.annotations.SerializedName;

public class MainSearch {
    @SerializedName("id")
    int pk;
    @SerializedName("name")
    String name;
    @SerializedName("address")
    String address;
    @SerializedName("distance")
    double distance;

    public MainSearch(int pk, String name, String address, double distance) {
        this.pk = pk;
        this.name = name;
        this.address = address;
        this.distance = distance;
    }

    public int getPk() {
        return pk;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getDistance() {
        return distance;
    }
}
