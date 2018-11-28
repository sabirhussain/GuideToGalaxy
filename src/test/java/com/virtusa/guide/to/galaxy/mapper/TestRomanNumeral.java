package com.virtusa.guide.to.galaxy.mapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestRomanNumeral {

    private RomanNumeral numeral = RomanNumeral.INSTANCE;

    @Before
    public void setup() {
        numeral.add("O", 0);
    }

    @Test
    public void shouldFindNumeral() {
        Assert.assertTrue(numeral.containsKey("O"));
    }

    @Test
    public void shouldNotFindNumeral() {
        Assert.assertTrue(!numeral.containsKey("xxxx"));
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotAddInvalidNumeral() {
        numeral.add(null, 1);
    }

    @Test
    public void shouldGetDigit() {
        Assert.assertTrue(0 == numeral.getValue("O"));
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailToGetNonExistingNumeral() {
        numeral.getValue("xxxx");
    }

}
