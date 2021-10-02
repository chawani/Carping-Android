package com.tourkakao.carping.Post.DTO;

public class PayResponse {
    boolean tms_result;
    String next_redirect_app_url;
    String android_app_scheme;

    public boolean isTms_result() {
        return tms_result;
    }

    public void setTms_result(boolean tms_result) {
        this.tms_result = tms_result;
    }

    public String getNext_redirect_app_url() {
        return next_redirect_app_url;
    }

    public void setNext_redirect_app_url(String next_redirect_app_url) {
        this.next_redirect_app_url = next_redirect_app_url;
    }

    public String getAndroid_app_scheme() {
        return android_app_scheme;
    }

    public void setAndroid_app_scheme(String android_app_scheme) {
        this.android_app_scheme = android_app_scheme;
    }
}
