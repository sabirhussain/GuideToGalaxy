package com.virtusa.guide.to.galaxy.vo;

public class ProductInfo {

    private String name;

    private String qty;

    public ProductInfo(String name, String qty) {
        this.name = name;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public String getQty() {
        return qty;
    }
}
