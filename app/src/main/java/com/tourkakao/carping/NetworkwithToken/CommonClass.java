package com.tourkakao.carping.NetworkwithToken;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommonClass {
    @SerializedName("success")
    boolean success;
    @SerializedName("code")
    String code;
    @SerializedName("data")
    ArrayList data;

    public CommonClass(){}

    public CommonClass(boolean success, String code, ArrayList data) {
        this.success = success;
        this.code = code;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList getData() {
        return data;
    }

    public void setData(ArrayList data) {
        this.data = data;
    }
}
