package com.virtusa.guide.to.galaxy.mapper;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Extra terrestrial numeral mapper.
 * 
 * @author sabir
 *
 */
public enum ETNumeral implements Numeral<String, String> {
    INSTANCE;

    private Map<String, String> mapper;

    private ETNumeral() {
        mapper = new HashMap<>();
    }

    @Override
    public boolean containsKey(String key) {
        return mapper.containsKey(key);
    }

    @Override
    public String getValue(String key) {
        if (!mapper.containsKey(key))
            throw new RuntimeException(key + " is not a ET Numeral.");

        return mapper.get(key);
    }

    @Override
    public void add(String key, String value) {
        if (StringUtils.isAnyBlank(key, value))
            throw new IllegalArgumentException("key and value must not be null.");

        mapper.put(key, value);
    }

}
