package com.virtusa.guide.to.galaxy.mapper;

public interface Numeral<K, V> {

    boolean containsKey(K key);

    V getValue(K key);

    void add(K key, V value);

}
