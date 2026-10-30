package com.company.cart;

import java.util.*;

public class CheckoutSummary {
    private final double subtotal, totalDiscount, finalTotal;
    private final List<String> lines;

    public CheckoutSummary(double s, double d, double f, List<String> l) {
        subtotal = s;
        totalDiscount = d;
        finalTotal = f;
        lines = List.copyOf(l);
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public double getFinalTotal() {
        return finalTotal;
    }

    public List<String> getLines() {
        return lines;
    }
}
