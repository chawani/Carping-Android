package com.tourkakao.carping.thisweekend.DataClass;

import com.google.gson.annotations.SerializedName;

public class Each_campsite {
    @SerializedName("text")
    String body;
    @SerializedName("image")
    String image;
    @SerializedName("source")
    String image_source;
    @SerializedName("sub_title")
    String title;
    @SerializedName("name")
    String campsite_name;
    @SerializedName("address")
    String address;
    @SerializedName("phone")
    String phone;
    @SerializedName("type")
    String type;
    @SerializedName("oper_day")
    String oper_day;
    @SerializedName("website")
    String website;
    @SerializedName("sub_facility")
    String sub_facility;

    public Each_campsite(String body, String image, String image_source, String title, String campsite_name, String address, String phone, String type, String oper_day, String website, String sub_facility) {
        this.body = body;
        this.image = image;
        this.image_source = image_source;
        this.title = title;
        this.campsite_name = campsite_name;
        this.address = address;
        this.phone = phone;
        this.type = type;
        this.oper_day = oper_day;
        this.website = website;
        this.sub_facility = sub_facility;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCampsite_name() {
        return campsite_name;
    }

    public void setCampsite_name(String campsite_name) {
        this.campsite_name = campsite_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOper_day() {
        return oper_day;
    }

    public void setOper_day(String oper_day) {
        this.oper_day = oper_day;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSub_facility() {
        return sub_facility;
    }

    public void setSub_facility(String sub_facility) {
        this.sub_facility = sub_facility;
    }
}
