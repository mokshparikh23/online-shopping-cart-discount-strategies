package com.company.cart;

public class GroceriesDiscountStrategy implements DiscountStrategy<GroceriesProduct> {
    private static final double RATE = 0.30;

    @Override
    public double calculateDiscount(GroceriesProduct type) {
        return type.getPrice() * RATE;
    }
}
