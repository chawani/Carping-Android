package com.tourkakao.carping.Home.ThemeDataClass;

import com.google.gson.annotations.SerializedName;

public class Search {
    @SerializedName("id")
    int pk;
    @SerializedName("name")
    String name;

    public Search(int pk, String name) {
        this.pk = pk;
        this.name = name;
    }

    public int getPk() {
        return pk;
    }

    public String getName() {
        return name;
    }
}
