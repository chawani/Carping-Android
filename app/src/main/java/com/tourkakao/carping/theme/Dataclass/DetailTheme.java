package com.tourkakao.carping.theme.Dataclass;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DetailTheme {
    @SerializedName("id")
    int id;
    @SerializedName("image")
    String image;
    @SerializedName("type")
    String type;
    @SerializedName("address")
    String address;
    @SerializedName("name")
    String name;
    @SerializedName("phone")
    String phone;
    @SerializedName("distance")
    float distance;
    @SerializedName("lat")
    float lat;
    @SerializedName("lon")
    float lon;
    @SerializedName("website")
    String website;
    @SerializedName("reservation")
    String reservation;
    @SerializedName("oper_day")
    String oper_day;
    @SerializedName("season")
    String season;
    @SerializedName("permissoin_date")
    String permission_date;
    @SerializedName("main_facility")
    String main_facility;
    @SerializedName("sub_facility")
    String sub_facility;
    @SerializedName("rental_item")
    String rental_items;
    @SerializedName("animal")
    String animal;
    @SerializedName("brazier")
    String brazier;
    @SerializedName("tags")
    ArrayList<String> tags;
    @SerializedName("bookmark_count")
    int bookmark_count;
    @SerializedName("is_bookmarked")
    boolean is_bookmarked;

    public DetailTheme(int id, String image, String type, String address, String name, String phone, float distance, float lat, float lon, String website, String reservation, String oper_day, String season, String permission_date, String main_facility, String sub_facility, String rental_items, String animal, String brazier, ArrayList<String> tags, int bookmark_count, boolean is_bookmarked) {
        this.id = id;
        this.image = image;
        this.type = type;
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.distance = distance;
        this.lat = lat;
        this.lon = lon;
        this.website = website;
        this.reservation = reservation;
        this.oper_day = oper_day;
        this.season = season;
        this.permission_date = permission_date;
        this.main_facility = main_facility;
        this.sub_facility = sub_facility;
        this.rental_items = rental_items;
        this.animal = animal;
        this.brazier = brazier;
        this.tags = tags;
        this.bookmark_count = bookmark_count;
        this.is_bookmarked = is_bookmarked;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getReservation() {
        return reservation;
    }

    public void setReservation(String reservation) {
        this.reservation = reservation;
    }

    public String getOper_day() {
        return oper_day;
    }

    public void setOper_day(String oper_day) {
        this.oper_day = oper_day;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getPermission_date() {
        return permission_date;
    }

    public void setPermission_date(String permission_date) {
        this.permission_date = permission_date;
    }

    public String getMain_facility() {
        return main_facility;
    }

    public void setMain_facility(String main_facility) {
        this.main_facility = main_facility;
    }

    public String getSub_facility() {
        return sub_facility;
    }

    public void setSub_facility(String sub_facility) {
        this.sub_facility = sub_facility;
    }

    public String getRental_items() {
        return rental_items;
    }

    public void setRental_items(String rental_items) {
        this.rental_items = rental_items;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getBrazier() {
        return brazier;
    }

    public void setBrazier(String brazier) {
        this.brazier = brazier;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public int getBookmark_count() {
        return bookmark_count;
    }

    public void setBookmark_count(int bookmark_count) {
        this.bookmark_count = bookmark_count;
    }

    public boolean isIs_bookmarked() {
        return is_bookmarked;
    }

    public void setIs_bookmarked(boolean is_bookmarked) {
        this.is_bookmarked = is_bookmarked;
    }
}
