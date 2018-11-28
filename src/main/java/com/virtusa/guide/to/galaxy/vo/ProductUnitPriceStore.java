package com.virtusa.guide.to.galaxy.vo;

import java.util.HashMap;
import java.util.Map;

public enum ProductUnitPriceStore {
    INSTANCE;

    private Map<String, Double> productUnitPrice = new HashMap<>();

    public void add(String name, Double unitPrice) {
        productUnitPrice.put(name, unitPrice);
    }

    public double getUnitPrice(String name) {
        if (!productUnitPrice.containsKey(name))
            throw new RuntimeException("Product not found - " + name);

        return productUnitPrice.get(name);
    }

    public boolean exists(String name) {
        return productUnitPrice.containsKey(name);
    }
}
