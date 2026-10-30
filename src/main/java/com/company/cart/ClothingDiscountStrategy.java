package com.company.cart;

public class ClothingDiscountStrategy implements DiscountStrategy<ClothingProduct> {
    private static final double RATE = 0.20;

    @Override
    public double calculateDiscount(ClothingProduct type) {
        return type.getPrice() * RATE;
    }
}
