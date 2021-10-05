package com.tourkakao.carping.Post.DTO;

public class PriceInfo {
    private String price;
    private String trade_fee;
    private String platform_fee;
    private String withholding_tax;
    private String vat;
    private String final_point;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTrade_fee() {
        return trade_fee;
    }

    public void setTrade_fee(String trade_fee) {
        this.trade_fee = trade_fee;
    }

    public String getPlatform_fee() {
        return platform_fee;
    }

    public void setPlatform_fee(String platform_fee) {
        this.platform_fee = platform_fee;
    }

    public String getWithholding_tax() {
        return withholding_tax;
    }

    public void setWithholding_tax(String withholding_tax) {
        this.withholding_tax = withholding_tax;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getFinal_point() {
        return final_point;
    }

    public void setFinal_point(String final_point) {
        this.final_point = final_point;
    }
}
