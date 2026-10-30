package com.company.cart;

import org.junit.*;

import static org.junit.Assert.*;

public class DiscountCartTest {
    private static final double D = .0001;

    @Test
    public void electronicsDiscountIsTenPercent() {
        ElectronicsProduct p = new ElectronicsProduct("E1", "Laptop", 1000);
        assertEquals(100, p.calculateDiscount(), D);
        assertEquals(900, p.getDiscountedPrice(), D);
    }

    @Test
    public void clothingDiscountIsTwentyPercent() {
        ClothingProduct p = new ClothingProduct("C1", "Jacket", 200);
        assertEquals(40, p.calculateDiscount(), D);
    }

    @Test
    public void groceriesDiscountIsThirtyPercent() {
        GroceriesProduct p = new GroceriesProduct("G1", "Rice", 100);
        assertEquals(30, p.calculateDiscount(), D);
    }

    @Test
    public void polymorphicListUsesOverriddenMethods() {
        Product[] p = {new ElectronicsProduct("E", "TV", 100), new ClothingProduct("C", "Shirt", 100),
                new GroceriesProduct("G", "Milk", 100)};
        assertEquals(60, p[0].calculateDiscount() + p[1].calculateDiscount() + p[2].calculateDiscount(), D);
    }

    @Test
    public void dynamicStrategyCanBeApplied() {
        ElectronicsProduct p = new ElectronicsProduct("E", "TV", 100);
        p.setDiscountStrategy(x -> 50);
        assertEquals(50, p.calculateDiscount(), D);
    }

    @Test(expected = NullPointerException.class)
    public void nullDynamicStrategyRejected() {
        new ElectronicsProduct("E", "TV", 100).setDiscountStrategy(null);
    }

    @Test
    public void cartTotalsAreCorrect() {
        ShoppingCart c = cart();
        assertEquals(1300, c.calculateTotalPrice(), D);
        assertEquals(170, c.calculateTotalDiscount(), D);
        assertEquals(1130, c.calculateTotalPriceWithDiscounts(), D);
    }

    @Test
    public void checkoutSummaryHasTotalsAndLines() {
        CheckoutSummary s = cart().checkout();
        assertEquals(3, s.getLines().size());
        assertEquals(1300, s.getSubtotal(), D);
        assertEquals(1130, s.getFinalTotal(), D);
    }

    @Test
    public void summaryTextIsDeterministic() {
        String s = cart().displayCheckoutSummary();
        assertTrue(s.contains("Subtotal: 1300.00"));
        assertTrue(s.contains("Total Discount: 170.00"));
        assertTrue(s.contains("Final Total: 1130.00"));
    }

    @Test(expected = IllegalStateException.class)
    public void emptyCheckoutRejected() {
        new ShoppingCart().checkout();
    }

    @Test(expected = NullPointerException.class)
    public void nullProductRejected() {
        new ShoppingCart().addProduct(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void blankIdRejected() {
        new ShoppingCart().addProduct(new ElectronicsProduct(" ", "TV", 100));
    }

    @Test(expected = IllegalArgumentException.class)
    public void blankNameRejected() {
        new ShoppingCart().addProduct(new ElectronicsProduct("E", " ", 100));
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativePriceRejected() {
        new ShoppingCart().addProduct(new ElectronicsProduct("E", "TV", -1));
    }

    @Test
    public void zeroPriceIsAllowed() {
        ShoppingCart c = new ShoppingCart();
        c.addProduct(new GroceriesProduct("G", "Sample", 0));
        assertEquals(0, c.calculateTotalPriceWithDiscounts(), D);
    }

    @Test(expected = IllegalStateException.class)
    public void duplicateIdIgnoringCaseRejected() {
        ShoppingCart c = new ShoppingCart();
        c.addProduct(new ElectronicsProduct("E1", "TV", 100));
        c.addProduct(new ClothingProduct(" e1 ", "Shirt", 50));
    }

    @Test
    public void productOrderIsPreserved() {
        ShoppingCart c = cart();
        assertEquals("E1", c.getProducts().get(0).getProductId());
        assertEquals("G1", c.getProducts().get(2).getProductId());
    }

    @Test
    public void exposedListIsUnmodifiable() {
        ShoppingCart c = cart();
        try {
            c.getProducts().clear();
            fail();
        } catch (UnsupportedOperationException expected) {
        }
    }

    @Test
    public void genericStrategyIsTypeSafeAndReusable() {
        DiscountStrategy<ElectronicsProduct> s = new ElectronicsDiscountStrategy();
        assertEquals(10, s.calculateDiscount(new ElectronicsProduct("E", "TV", 100)), D);
    }

    private ShoppingCart cart() {
        ShoppingCart c = new ShoppingCart();
        c.addProduct(new ElectronicsProduct("E1", "Laptop", 1000));
        c.addProduct(new ClothingProduct("C1", "Jacket", 200));
        c.addProduct(new GroceriesProduct("G1", "Rice", 100));
        return c;
    }
}
