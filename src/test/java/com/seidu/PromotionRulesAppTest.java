package com.seidu;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import org.hamcrest.Matcher;
import org.junit.*;
import org.mockito.Mockito;
//import org.mockitoutil.ClassLoaders;

public class PromotionRulesAppTest {

  private static final List<Product> cart = new ArrayList<>();
  private static final Map<String, ProductInfo> prodInfo = new HashMap<>();
  private static  Map<String, Long> codeToQuantity = new HashMap<>();
  private static final Map<String, BigDecimal> totalPriceOfQuantityOfSpecificItemPurchased = new HashMap<>();
  private static BigDecimal finalTotal = new BigDecimal(00.00).setScale(2,BigDecimal.ROUND_HALF_EVEN);

 // private static final Scanner scanner = new Scanner(System.in);
  private static  String itemCode ;
  private static  BigDecimal promotionPrice;
  private static  BigDecimal originalPrice;
  private  static   ProductBuilder productBuilder;
  private  static BigDecimal percentageReduction;

  static {
    prodInfo.put("001", new com.seidu.ProductInfo("001","Travel Card Holder",
            new BigDecimal(9.25)));
    prodInfo.put("002", new com.seidu.ProductInfo("002","Personalized Cufflinks",
            new BigDecimal(45.00)));
    prodInfo.put("003", new com.seidu.ProductInfo("003","Kid's T-Shirt",
            new BigDecimal(19.95)));
  }

  @Test
  public void testScan(){
      Scan scanner= new Scan();

      String input = "001";
      InputStream in = new ByteArrayInputStream(input.getBytes());
      System.setIn(in);

      List<Product> products = scanner.scan();
      assertNotNull(products);

      //assertEquals("001", scanner.getScannerInput());
  }


  @Test
  public void testMultiplyItemQuantityByItemPrice(){

  }

  @Test
    public  void testCountItemByProductCode() {

      ProductBuilder productBuilder = new ProductBuilder();
      List<Product> productCodes = new ArrayList<>();
      String[]prodCodes = {"001", "002","003","001"};
      Product prod = null;

      for (int i = 0; i < 4; i++ ) {
          prod = productBuilder.with(builder -> {
          }).build();
          prod.productCode = prodCodes[i];
          if (prod.productCode.equalsIgnoreCase("001")) {
              prod.price = new BigDecimal(9.25);
              prod.name = "Travel Card Holder";
          } else if (prod.productCode.equalsIgnoreCase("003")) {
              prod.price = new BigDecimal(19.95);
              prod.name = "Kids T-Shirt";
          } else if (prod.productCode.equalsIgnoreCase("002")) {
              prod.price = new BigDecimal(45.00);
              prod.name = "Personalised Cuff Links";
          }
          productCodes.add(prod);
      }
      codeToQuantity = Main.countItemByProductCode(productCodes);
      assertEquals(codeToQuantity.get("001"), 2);
      assertEquals(codeToQuantity.get("002"), 1);
      assertEquals(codeToQuantity.get("003"), 1);
      assertEquals(3, codeToQuantity.size());
      }


    /**
     * Method to price change of a specific product by productCode.
     *
     * The price of the product with this productCode will change
     * to a new price. The change can be either lower or higher
     * depending on business decision.
     *
     **/
    @Test
    public void testProcessPromotionPrice(){

        Map<String, BigDecimal> codeToPrice  = new HashMap<>();

        codeToPrice.put("001", new BigDecimal(18.50).setScale(2,BigDecimal.ROUND_HALF_EVEN));
        codeToPrice.put("003", new BigDecimal(19.95).setScale(2,BigDecimal.ROUND_HALF_EVEN));

        Map<String, Long> codeToCount  = new HashMap<>();
        codeToCount.put("001", new Long(2));
        codeToCount.put("002", new Long(1));
        codeToCount.put("003", new Long(1));

        Main main = new Main( new BigDecimal(8.50).setScale(2,BigDecimal.ROUND_HALF_EVEN),
                new BigDecimal(9.25).setScale(2,BigDecimal.ROUND_HALF_EVEN),"001",
                new BigDecimal(0.1) );

        PromotionRules<String, BigDecimal, Boolean> promotionRules =
                (a, b) -> a.equalsIgnoreCase(main.getItemCode()) && b.compareTo(main.getOriginalPrice()) > 0;

        Map<String, BigDecimal>promoReduction = main.calculatePromotionPrice(codeToPrice,codeToCount,promotionRules);
        assertEquals(new BigDecimal(17.00).setScale(2,BigDecimal.ROUND_HALF_EVEN), promoReduction.get("001"));

    }



}

