package com.tourkakao.carping.MypageMainActivities.DTO;

import java.util.ArrayList;
import java.util.List;

public class ScrapData {
    ArrayList<Campsite> campsite;
    ArrayList<MyCarpingPost> autocamp;

    public ArrayList<Campsite> getCampsite() {
        return campsite;
    }

    public void setCampsite(ArrayList<Campsite> campsite) {
        this.campsite = campsite;
    }

    public ArrayList<MyCarpingPost> getAutocamp() {
        return autocamp;
    }

    public void setAutocamp(ArrayList<MyCarpingPost> autocamp) {
        this.autocamp = autocamp;
    }
}
