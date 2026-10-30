package com.company.cart;

public class ElectronicsDiscountStrategy implements DiscountStrategy<ElectronicsProduct> {
    private static final double RATE = 0.10;

    @Override
    public double calculateDiscount(ElectronicsProduct type) {
        return type.getPrice() * RATE;
    }
}
