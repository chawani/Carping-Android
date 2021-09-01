package com.tourkakao.carping.EcoCarping.DTO;

public class Comment {
    private String id;
    private String user;
    private String username;
    private String profile;
    private String level;
    private String badge;
    private String text;
    private String root;
    private String created_at;

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getUsername() {
        return username;
    }

    public String getProfile() {
        return profile;
    }

    public String getLevel() {
        return level;
    }

    public String getBadge() {
        return badge;
    }

    public String getText() {
        return text;
    }

    public String getRoot() {
        return root;
    }

    public String getCreated_at() {
        return created_at;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", user='" + user + '\'' +
                ", username='" + username + '\'' +
                ", profile='" + profile + '\'' +
                ", level='" + level + '\'' +
                ", badge='" + badge + '\'' +
                ", text='" + text + '\'' +
                ", root='" + root + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
