package com.tourkakao.carping.Home.StoreDataClass;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("id")
    int pk;
    @SerializedName("item")
    String name;
    @SerializedName("image")
    String image;
    @SerializedName("price")
    String price;

    public Product(int pk, String name, String image, String price) {
        this.pk = pk;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
