package com.seidu;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MultiplyItemByItemQuantityTest {

    private static Map<String, ProductInfo> prodInfo = new HashMap<>();
    private static  Map<String, Long> codeToQuantityPurchased = new HashMap<>();
    private static Map<String, BigDecimal> totalPriceOfQuantityOfSpecificItemPurchased = new HashMap<>();
    private  static   ProductBuilder productBuilder = new ProductBuilder();

    static {
        prodInfo.put("001", new ProductInfo("001","Travel Card Holder",
                new BigDecimal(9.25).setScale(2,BigDecimal.ROUND_HALF_EVEN)));
        prodInfo.put("002", new ProductInfo("002","Personalized Cufflinks",
                new BigDecimal(45.00).setScale(2,BigDecimal.ROUND_HALF_EVEN)));
        prodInfo.put("003", new ProductInfo("003","Kid's T-Shirt",
                new BigDecimal(19.95).setScale(2,BigDecimal.ROUND_HALF_EVEN)));
    }
    @Test
    public void testMultiplyItemQuantityByItemPrice(){

        codeToQuantityPurchased.put("001",new Long(2));
        codeToQuantityPurchased.put("003",new Long(1));

        Map<String, BigDecimal>  map = Main.multiplyItemQuantityByItemPrice(codeToQuantityPurchased);
        assertEquals(new BigDecimal(18.50).setScale(2,BigDecimal.ROUND_HALF_EVEN) ,
                map.get("001").setScale(2,BigDecimal.ROUND_HALF_EVEN) );
        assertEquals(new BigDecimal(19.95).setScale(2,BigDecimal.ROUND_HALF_EVEN) ,
                map.get("003").setScale(2,BigDecimal.ROUND_HALF_EVEN));
    }

    @Test
    public void testMultiplyItemQuantityByItemPrice2(){

        codeToQuantityPurchased.put("001",new Long(3));
        codeToQuantityPurchased.put("003",new Long(2));
        codeToQuantityPurchased.put("002",new Long(2));

        Map<String, BigDecimal>  map = Main.multiplyItemQuantityByItemPrice(codeToQuantityPurchased);
        assertEquals(new BigDecimal(27.75).setScale(2,BigDecimal.ROUND_HALF_EVEN) ,
                map.get("001").setScale(2,BigDecimal.ROUND_HALF_EVEN) );
        assertEquals(new BigDecimal(39.90).setScale(2,BigDecimal.ROUND_HALF_EVEN) ,
                map.get("003").setScale(2,BigDecimal.ROUND_HALF_EVEN));
        assertEquals(new BigDecimal(90.00).setScale(2,BigDecimal.ROUND_HALF_EVEN) ,
                map.get("002").setScale(2,BigDecimal.ROUND_HALF_EVEN));
    }
}
