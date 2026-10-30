package com.company.cart;

public abstract class Product {
    private String productId;
    private String name;
    private String category;
    private double price;

    protected Product(String productId, String name, String category, double price) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public abstract double calculateDiscount();

    public double getDiscountedPrice() {
        return price - calculateDiscount();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String v) {
        productId = v;
    }

    public String getName() {
        return name;
    }

    public void setName(String v) {
        name = v;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String v) {
        category = v;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double v) {
        price = v;
    }
}
