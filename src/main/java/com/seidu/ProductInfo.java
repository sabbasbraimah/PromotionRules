package com.seidu;

import java.math.BigDecimal;

public class ProductInfo {

    private final String code;
    private final String name;
    private  BigDecimal price;

    public ProductInfo(String code, String name, BigDecimal price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
