package com.tourkakao.carping.EcoCarping.DTO;

public class PostComment {
    private String user;
    private String eco;
    private String text;
    //private String root;

    public PostComment(String user, String eco, String text) {
        this.user = user;
        this.eco = eco;
        this.text = text;
        //this.root = root;
    }

    public String getUser() {
        return user;
    }

    public void setName(String user) {
        this.user = user;
    }

    public String getEco() {
        return eco;
    }

    public void setEco(String eco) {
        this.eco = eco;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
