package com.tourkakao.carping.Home.EcoDataClass;

public class MoreInfo {
    private int eco_percentage;
    private int monthly_eco_count;

    public MoreInfo(int eco_percentage, int monthly_eco_count) {
        this.eco_percentage = eco_percentage;
        this.monthly_eco_count = monthly_eco_count;
    }

    public int getEco_percentage() {
        return eco_percentage;
    }

    public int getMonthly_eco_count() {
        return monthly_eco_count;
    }
}
