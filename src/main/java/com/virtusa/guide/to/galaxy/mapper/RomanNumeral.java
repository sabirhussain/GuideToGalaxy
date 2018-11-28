package com.virtusa.guide.to.galaxy.mapper;

import java.util.HashMap;
import java.util.Map;

public enum RomanNumeral implements Numeral<String, Integer> {
    INSTANCE;
    private Map<String, Integer> mapper;

    private RomanNumeral() {
        mapper = new HashMap<>();
        mapper.put("I", 1);
        mapper.put("V", 5);
        mapper.put("X", 10);
        mapper.put("L", 50);
        mapper.put("C", 100);
        mapper.put("D", 500);
        mapper.put("M", 1000);
    }

    @Override
    public boolean containsKey(String key) {
        return mapper.containsKey(key);
    }

    @Override
    public Integer getValue(String key) {
        if (!mapper.containsKey(key))
            throw new RuntimeException(key + " is not a Roman Numeral.");

        return mapper.get(key);
    }

    @Override
    public void add(String key, Integer value) {
        if (key == null || value == null)
            throw new IllegalArgumentException("key and value must not be null.");

        mapper.put(key, value);
    }

}
