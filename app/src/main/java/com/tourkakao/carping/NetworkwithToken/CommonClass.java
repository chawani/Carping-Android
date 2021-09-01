package com.tourkakao.carping.NetworkwithToken;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommonClass {
    @SerializedName("success")
    boolean success;
    @SerializedName("error_message")
    String error_message;
    @SerializedName("code")
    int code;
    @SerializedName("data")
    ArrayList data;

    public CommonClass(){}

    public CommonClass(boolean success, String error_message, int code, ArrayList data) {
        this.success = success;
        this.error_message = error_message;
        this.code = code;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList getData() {
        return data;
    }

    public void setData(ArrayList data) {
        this.data = data;
    }
}
