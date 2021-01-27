package com.seidu;

import java.math.BigDecimal;
import java.util.*;

public class Scan {


    private static List<Product> cart = new ArrayList<>();
    private static Map<String, ProductInfo> prodInfo = new HashMap<>();
    private static  Map<String, Long> codeToQuantity = new HashMap<>();
    private static Map<String,BigDecimal> totalPriceOfQuantityOfSpecificItemPurchased = new HashMap<>();
    private static BigDecimal finalTotal = new BigDecimal(00.00).setScale(2,BigDecimal.ROUND_HALF_EVEN);

    private static String itemCode ;
    private static BigDecimal promotionPrice;
    private  static   ProductBuilder productBuilder = new ProductBuilder();



    //Main promPrice = new Main(new BigDecimal(8.50),  new BigDecimal(9.25), "001",
        //    PercentageReduction.TEN.getReduceBy());
    static {
        prodInfo.put("001", new ProductInfo("001","Travel Card Holder",
                new BigDecimal(9.25)));
        prodInfo.put("002", new ProductInfo("002","Personalized Cufflinks",
                new BigDecimal(45.00)));
        prodInfo.put("003", new ProductInfo("003","Kid's T-Shirt",
                new BigDecimal(19.95)));
    }


    public  static String getScannerInput(){
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    public  static List<Product> scan(){
        String validCodeExpression = "^00[1-3]$";

        do {
            System.out.println(" Scan item or press 'Quit' to stop");
            itemCode = getScannerInput();
            if (!itemCode.equalsIgnoreCase("Quit") && itemCode != null) {
                if (itemCode.matches(validCodeExpression) == false){
                    continue;
                }
                ProductInfo proInfo = prodInfo.get(itemCode);
                Product product= productBuilder.with(builder -> {
                    builder.productCode = proInfo.getCode();
                    builder.name = proInfo.getName();
                    builder.price = proInfo.getPrice();
                }).build();
                cart.add(product);
            }
        } while (!itemCode.equalsIgnoreCase("Quit")  );
        return cart;
    }

}
