package com.tourkakao.carping.Home.EcoDataClass;

import java.util.ArrayList;

public class EcoRankingData {
    private ArrayList<EcoRanking> rankingUserInfo;

    public EcoRankingData(ArrayList<EcoRanking> rankingUserInfo) {
        this.rankingUserInfo = rankingUserInfo;
    }

    public ArrayList<EcoRanking> getRankingUserInfo() {
        return rankingUserInfo;
    }
}
