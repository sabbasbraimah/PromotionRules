package com.seidu;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CountByItemCodeTest {

    private static Map<String, ProductInfo> prodInfo = new HashMap<>();
    private static  Map<String, Long> codeToQuantityPurchased = new HashMap<>();
    private static Map<String, BigDecimal> totalPriceOfQuantityOfSpecificItemPurchased = new HashMap<>();
    private  static   ProductBuilder productBuilder = new ProductBuilder();

    static {
        prodInfo.put("001", new ProductInfo("001","Travel Card Holder",
                new BigDecimal(9.25)));
        prodInfo.put("002", new ProductInfo("002","Personalized Cufflinks",
                new BigDecimal(45.00)));
        prodInfo.put("003", new ProductInfo("003","Kid's T-Shirt",
                new BigDecimal(19.95)));
    }

   @Test
            public void testCountItemByCode1(){
                List<Product> cart = new ArrayList<>();
                String[]itemCode = { "001","003","001"};
                for (int i = 0; i < itemCode.length; i++) {
                    String code = itemCode[i];
                    Product product = productBuilder.with(builder -> {
                        builder.productCode = prodInfo.get(code).getCode();
                        builder.name = prodInfo.get(code).getName();
                        builder.price = prodInfo.get(code).getPrice();
                    }).build();
                    cart.add(product);
        }
        Map<String, Long> numberOfEachItem = Main.countItemByProductCode(cart);
        assertEquals(numberOfEachItem.get("001"), 2);
        assertEquals(numberOfEachItem.get("003"), 1);
        assertEquals(numberOfEachItem.get("002"), null);

    }

    @Test
    public void testCountItemByCode2(){
        List<Product> cart = new ArrayList<>();
        String[]itemCode2 = { "001","003","002"};
        for (int i = 0; i < itemCode2.length; i++) {
            String code = itemCode2[i];
            Product product = productBuilder.with(builder -> {
                builder.productCode = prodInfo.get(code).getCode();
                builder.name = prodInfo.get(code).getName();
                builder.price = prodInfo.get(code).getPrice();
            }).build();
            cart.add(product);
        }
        Map<String, Long> numberOfEachItem = Main.countItemByProductCode(cart);
        assertEquals(numberOfEachItem.get("001"), 1);
        assertEquals(numberOfEachItem.get("002"), 1);
        assertEquals(numberOfEachItem.get("003"), 1);
    }

    @Test
    public void testCountItemByCode3(){
        List<Product> cart = new ArrayList<>();
        String[]itemCode3 = { "001","003","002","001"};
        for (int i = 0; i < itemCode3.length; i++) {
            String code = itemCode3[i];
            Product product = productBuilder.with(builder -> {
                builder.productCode = prodInfo.get(code).getCode();
                builder.name = prodInfo.get(code).getName();
                builder.price = prodInfo.get(code).getPrice();
            }).build();
            cart.add(product);
        }
        Map<String, Long> numberOfEachItem = Main.countItemByProductCode(cart);
        assertEquals(numberOfEachItem.get("001"), 2);
        assertEquals(numberOfEachItem.get("002"), 1);
        assertEquals(numberOfEachItem.get("003"), 1);
    }
}
