package com.seidu;

@FunctionalInterface
public interface PromotionRules<T,U,R> {
    boolean test(T t, U u);
}
