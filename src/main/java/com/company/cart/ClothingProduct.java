package com.company.cart;

import java.util.Objects;

public class ClothingProduct extends Product {
    private DiscountStrategy<ClothingProduct> discountStrategy;

    public ClothingProduct(String productId, String name, double price) {
        super(productId, name, "Clothing", price);
        this.discountStrategy = new ClothingDiscountStrategy();
    }

    @Override
    public double calculateDiscount() {
        return discountStrategy.calculateDiscount(this);
    }

    public void setDiscountStrategy(DiscountStrategy<ClothingProduct> s) {
        this.discountStrategy = Objects.requireNonNull(s, "discountStrategy must not be null");
    }
}
