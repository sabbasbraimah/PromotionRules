package com.seidu;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class ProductBuilder {

      String productCode;
      String name;
      BigDecimal price;
      ProductBuilder productBuilder;


     ProductBuilder  with(Consumer<ProductBuilder> productBuilder){
          productBuilder.accept(this);
          return this;
     }

     public String getProductCode() {
          return productCode;
     }

     public String getName() {
          return name;
     }

     public BigDecimal getPrice() {
          return price;
     }

     public ProductBuilder getProductBuilder() {
          return productBuilder;
     }

     public com.seidu.Product build(){
          return  new com.seidu.Product(this);
     }
}
