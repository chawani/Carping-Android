package com.tourkakao.carping.Post.DTO;

public class PriceInfo {
    private int price;
    private int trade_fee;
    private int platform_fee;
    private int withholding_tax;
    private int vat;
    private int final_point;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTrade_fee() {
        return trade_fee;
    }

    public void setTrade_fee(int trade_fee) {
        this.trade_fee = trade_fee;
    }

    public int getPlatform_fee() {
        return platform_fee;
    }

    public void setPlatform_fee(int platform_fee) {
        this.platform_fee = platform_fee;
    }

    public int getWithholding_tax() {
        return withholding_tax;
    }

    public void setWithholding_tax(int withholding_tax) {
        this.withholding_tax = withholding_tax;
    }

    public int getVat() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public int getFinal_point() {
        return final_point;
    }

    public void setFinal_point(int final_point) {
        this.final_point = final_point;
    }
}
