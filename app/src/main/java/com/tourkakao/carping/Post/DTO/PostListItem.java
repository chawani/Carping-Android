package com.tourkakao.carping.Post.DTO;

public class PostListItem {
    String image;
    String title;
    String name;

    public PostListItem(String image, String title, String name) {
        this.image = image;
        this.title = title;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
