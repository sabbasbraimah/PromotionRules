package com.seidu;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static List<com.seidu.Product> cart = new ArrayList<>();
    private static Map<String, ProductInfo> prodInfo = new HashMap<>();
    private static  Map<String, Long> codeToQuantity = new HashMap<>();
    private static Map<String,BigDecimal> totalPriceOfQuantityOfSpecificItemPurchased = new HashMap<>();
    private static BigDecimal finalTotal = new BigDecimal(00.00).setScale(2,BigDecimal.ROUND_HALF_EVEN);

    //private static final Scanner scanner = new Scanner(System.in);
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

    public Main( BigDecimal promotionPrice,  BigDecimal originalPrice,String itemCode,
                 BigDecimal percentageReduction ) {
        this.promotionPrice = promotionPrice;
        this.originalPrice = originalPrice;
        this.itemCode = itemCode ;
        this.percentageReduction = percentageReduction;
        productBuilder = new ProductBuilder();
    }

    public static void main(String[] args) {

        Main promPrice = new Main(new BigDecimal(8.50),
                new BigDecimal(9.25), "001", percentageReduction);
        Scan scan = new Scan();
        Map<String, Long> numberOfEachItem = countItemByProductCode(scan.scan());
        System.out.println( "Quantity by Item Price  : " +
                multiplyItemQuantityByItemPrice(numberOfEachItem));
        Map<String, BigDecimal> processedPrice =    processPromotionPrice(  numberOfEachItem);
        calculateTotals(processedPrice);
        displayTotalPrice(percentageReduction);
    }

    public static String getItemCode() {
        return itemCode;
    }

    public static BigDecimal getPromotionPrice() {
        return promotionPrice;
    }

    public static BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public static Map<String, BigDecimal> processPromotionPrice(  Map<String, Long> numberOfEachItem) {

        PromotionRules<String, BigDecimal, Boolean> promotionRules =
                (a, b) -> a.equalsIgnoreCase(getItemCode()) && b.compareTo(getOriginalPrice()) > 0;

        Map<String, BigDecimal> pricesAfterApplyingPromotionRules =
                calculatePromotionPrice( multiplyItemQuantityByItemPrice(numberOfEachItem), codeToQuantity,
                        promotionRules);
        return pricesAfterApplyingPromotionRules;
    }

    public static Map<String, Long>  countItemByProductCode(List<Product> productCodes){
            codeToQuantity = productCodes.parallelStream()
                    .collect(Collectors.groupingBy(product -> product.getProductCode(), Collectors.counting()));
            return codeToQuantity;
    }

    public static  Map<String, BigDecimal> multiplyItemQuantityByItemPrice(Map<String,Long> items) {
        Set<Map.Entry<String, Long>> entries = items.entrySet();

        for (Map.Entry<String, Long> entry : entries) {
            String code = entry.getKey();
            long quantity = entry.getValue();
            if (code.equalsIgnoreCase(prodInfo.get(code).getCode())) {
                BigDecimal total = prodInfo.get(code).getPrice().setScale(2, BigDecimal.ROUND_HALF_EVEN)
                        .multiply(new BigDecimal(quantity).setScale(2,BigDecimal.ROUND_HALF_EVEN));
                totalPriceOfQuantityOfSpecificItemPurchased.put(code, total);
            }
        }
        return totalPriceOfQuantityOfSpecificItemPurchased;
    }

    public static Map<String, BigDecimal> calculatePromotionPrice(Map<String, BigDecimal> codeToPrice,
                                                                  Map<String, Long> products,
            PromotionRules<String,BigDecimal,Boolean>promoRules){
        for (Map.Entry<String, BigDecimal> entry : codeToPrice.entrySet()){
            String productCode = entry.getKey();
            BigDecimal amount = entry.getValue().setScale(2,BigDecimal.ROUND_HALF_EVEN);

           if( promoRules.test(productCode,amount) ){
               prodInfo.get(productCode).setPrice(getPromotionPrice().setScale(2,BigDecimal.ROUND_HALF_EVEN));
               BigDecimal total = prodInfo.get(productCode).getPrice().setScale(2,BigDecimal.ROUND_HALF_EVEN)
                       .multiply(new BigDecimal(  products.get(productCode)  ))
                               .setScale(2,BigDecimal.ROUND_HALF_EVEN);
               totalPriceOfQuantityOfSpecificItemPurchased.put(productCode, total);
           }
        }
        return totalPriceOfQuantityOfSpecificItemPurchased;
    }

    public static void calculateTotals( Map<String,BigDecimal> pra){
        for(Map.Entry<String, BigDecimal> entry : pra.entrySet()){
            String key = entry.getKey();
            BigDecimal value = entry.getValue().setScale(2,BigDecimal.ROUND_HALF_EVEN);
            System.out.println("                              ");
            System.out.println( key +"  |  " + prodInfo.get(key).getName() + "  |  " +
                    codeToQuantity.get(key) + "  |  " + value.setScale(2,BigDecimal.ROUND_HALF_EVEN) );
            finalTotal = finalTotal.add(value);
        }
        System.out.println("                              ");
    }

    public static void displayTotalPrice(BigDecimal percentageReduction){
        if(finalTotal.compareTo(new BigDecimal(60.00)) > 0){
            BigDecimal reduction = new BigDecimal(00.00).setScale(BigDecimal.ROUND_HALF_EVEN);
            reduction = finalTotal.multiply(percentageReduction
                    .setScale(2,BigDecimal.ROUND_HALF_EVEN));
            finalTotal = finalTotal.subtract(reduction);
        }
        System.out.println("Final Total   :" + finalTotal
                .setScale(2, BigDecimal.ROUND_HALF_EVEN));
    }
}


