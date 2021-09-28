package com.tourkakao.carping.registernewshare.Dataclass;

import com.google.gson.annotations.SerializedName;

public class Sigungu {
    @SerializedName("sido")
    String sido;
    @SerializedName("sigungu")
    String sigungu;

    public Sigungu(String sido, String sigungu) {
        this.sido = sido;
        this.sigungu = sigungu;
    }

    public String getSido() {
        return sido;
    }

    public void setSido(String sido) {
        this.sido = sido;
    }

    public String getSigungu() {
        return sigungu;
    }

    public void setSigungu(String sigungu) {
        this.sigungu = sigungu;
    }
}
