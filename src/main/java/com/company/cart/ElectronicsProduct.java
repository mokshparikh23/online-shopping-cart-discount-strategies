package com.company.cart;

import java.util.Objects;

public class ElectronicsProduct extends Product {
    private DiscountStrategy<ElectronicsProduct> discountStrategy;

    public ElectronicsProduct(String productId, String name, double price) {
        super(productId, name, "Electronics", price);
        this.discountStrategy = new ElectronicsDiscountStrategy();
    }

    @Override
    public double calculateDiscount() {
        return discountStrategy.calculateDiscount(this);
    }

    public void setDiscountStrategy(DiscountStrategy<ElectronicsProduct> s) {
        this.discountStrategy = Objects.requireNonNull(s, "discountStrategy must not be null");
    }
}
