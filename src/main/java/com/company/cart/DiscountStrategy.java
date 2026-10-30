package com.company.cart;

@FunctionalInterface
public interface DiscountStrategy<T> {
    double calculateDiscount(T type);
}
