package com.company.cart;

public class Main {
    public static void main(String[] a) {
        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(new ElectronicsProduct("E1", "Laptop", 1000));
        cart.addProduct(new ClothingProduct("C1", "Jacket", 200));
        cart.addProduct(new GroceriesProduct("G1", "Rice", 100));
        System.out.println(cart.displayCheckoutSummary());
    }
}
