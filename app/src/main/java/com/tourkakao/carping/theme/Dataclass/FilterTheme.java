package com.tourkakao.carping.theme.Dataclass;

import com.google.gson.annotations.SerializedName;

public class FilterTheme {
    @SerializedName("theme")
    String theme;
    @SerializedName("sort")
    String sort;
    @SerializedName("select")
    String select;
    @SerializedName("lat")
    double lat;
    @SerializedName("lon")
    double lon;

    public FilterTheme(String theme, String sort, String select, double lat, double lon) {
        this.theme = theme;
        this.sort = sort;
        this.select = select;
        this.lat = lat;
        this.lon = lon;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
