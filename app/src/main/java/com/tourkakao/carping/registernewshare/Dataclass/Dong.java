package com.tourkakao.carping.registernewshare.Dataclass;

import com.google.gson.annotations.SerializedName;

public class Dong {
    @SerializedName("id")
    int id;
    @SerializedName("sido")
    String sido;
    @SerializedName("sigungu")
    String sigungu;
    @SerializedName("dong")
    String dong;

    public Dong(int id, String sido, String sigungu, String dong) {
        this.id = id;
        this.sido = sido;
        this.sigungu = sigungu;
        this.dong = dong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSido() {
        return sido;
    }

    public void setSiso(String sido) {
        this.sido = sido;
    }

    public String getSigungu() {
        return sigungu;
    }

    public void setSigungu(String sigungu) {
        this.sigungu = sigungu;
    }

    public String getDong() {
        return dong;
    }

    public void setDong(String dong) {
        this.dong = dong;
    }
}
