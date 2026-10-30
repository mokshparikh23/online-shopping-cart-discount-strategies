package com.company.cart;

import java.util.Objects;

public class GroceriesProduct extends Product {
    private DiscountStrategy<GroceriesProduct> discountStrategy;

    public GroceriesProduct(String productId, String name, double price) {
        super(productId, name, "Groceries", price);
        this.discountStrategy = new GroceriesDiscountStrategy();
    }

    @Override
    public double calculateDiscount() {
        return discountStrategy.calculateDiscount(this);
    }

    public void setDiscountStrategy(DiscountStrategy<GroceriesProduct> s) {
        this.discountStrategy = Objects.requireNonNull(s, "discountStrategy must not be null");
    }
}
