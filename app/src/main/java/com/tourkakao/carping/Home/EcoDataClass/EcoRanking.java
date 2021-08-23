package com.tourkakao.carping.Home.EcoDataClass;

public class EcoRanking {
    private String id;
    private String username;
    private String image;
    private int level;
    private String badge;
    private int eco_count;

    public EcoRanking(String id, String username, String image, int level, String badge, int eco_count) {
        this.id = id;
        this.username = username;
        this.image = image;
        this.level = level;
        this.badge = badge;
        this.eco_count = eco_count;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getImage() {
        return image;
    }

    public int getLevel() {
        return level;
    }

    public String getBadge() {
        return badge;
    }

    public int getEco_count() {
        return eco_count;
    }
}
