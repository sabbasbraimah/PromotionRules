package com.seidu;

import java.math.BigDecimal;

public class Product {

      String productCode;
      String name;
     BigDecimal price;

    public Product(ProductBuilder productBuilder){
        this.productCode = productBuilder.productCode;
        this.name = productBuilder.name;
        this.price = productBuilder.price;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!productCode.equals(product.productCode)) return false;
        if (!name.equals(product.name)) return false;
        return price.equals(product.price);
    }

    @Override
    public int hashCode() {
        int result = productCode.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productCode='" + productCode + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
