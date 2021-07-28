package com.tourkakao.carping.Login;

import com.google.gson.annotations.SerializedName;

public class Google_User_Info {
    @SerializedName("pk")
    private final int pk;
    @SerializedName("username")
    private final String username;
    @SerializedName("email")
    private final String email;
    @SerializedName("profile")
    private final Profile profile;

    private class Profile{
        @SerializedName("image")
        private final String image;

        public Profile(String image) {
            this.image = image;
        }

        public String getImage() {
            return image;
        }
    }

    public Google_User_Info(int pk, String username, String email, Profile profile) {
        this.pk = pk;
        this.username = username;
        this.email = email;
        this.profile = profile;
    }

    public int getPk() {
        return pk;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getProfile() {
        return profile.getImage();
    }

    @Override
    public String toString() {
        return "Google_User_Info{" +
                "pk=" + pk +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", profile.image=" + profile.getImage() +
                '}';
    }
}

