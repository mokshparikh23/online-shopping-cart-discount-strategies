package com.company.cart;

import java.util.*;

public class ShoppingCart {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        Objects.requireNonNull(product, "product must not be null");
        String productId = requireText(product.getProductId(), "productId");
        requireText(product.getName(), "name");
        requireText(product.getCategory(), "category");
        double price = product.getPrice();
        if (!Double.isFinite(price) || price < 0) {
            throw new IllegalArgumentException("price must be a non-negative number: " + price);
        }
        for (Product existing : products) {
            if (existing.getProductId().equalsIgnoreCase(productId)) {
                throw new IllegalStateException("Duplicate productId: " + productId);
            }
        }
        product.setProductId(productId);
        products.add(product);
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(new ArrayList<>(products));
    }

    public double calculateTotalPrice() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }

    public double calculateTotalDiscount() {
        double total = 0;
        for (Product product : products) {
            total += product.calculateDiscount(); // runtime dispatch to each subclass override
        }
        return total;
    }

    public double calculateTotalPriceWithDiscounts() {
        return calculateTotalPrice() - calculateTotalDiscount();
    }

    public CheckoutSummary checkout() {
        if (products.isEmpty()) {
            throw new IllegalStateException("Cannot checkout an empty cart");
        }
        List<String> lines = new ArrayList<>();
        for (Product product : products) {
            lines.add(String.format(Locale.ROOT, "%s | %s | %s | Price: %.2f | Discount: %.2f | Final: %.2f",
                    product.getProductId(), product.getName(), product.getCategory(),
                    product.getPrice(), product.calculateDiscount(), product.getDiscountedPrice()));
        }
        return new CheckoutSummary(calculateTotalPrice(), calculateTotalDiscount(),
                calculateTotalPriceWithDiscounts(), lines);
    }

    public String displayCheckoutSummary() {
        CheckoutSummary summary = checkout();
        // Locale.ROOT and '\n' keep the text identical on every machine.
        StringBuilder sb = new StringBuilder("Checkout Summary\n");
        for (String line : summary.getLines()) {
            sb.append(line).append('\n');
        }
        sb.append(String.format(Locale.ROOT, "Subtotal: %.2f\n", summary.getSubtotal()));
        sb.append(String.format(Locale.ROOT, "Total Discount: %.2f\n", summary.getTotalDiscount()));
        sb.append(String.format(Locale.ROOT, "Final Total: %.2f", summary.getFinalTotal()));
        return sb.toString();
    }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value.trim();
    }
}
