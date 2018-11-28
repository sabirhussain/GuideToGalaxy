package com.virtusa.guide.to.galaxy.mapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestETNumeral {
    private ETNumeral numeral = ETNumeral.INSTANCE;

    @Before
    public void setup() {
        numeral.add("glob", "I");
        numeral.add("prok", "V");
        numeral.add("pish", "X");
        numeral.add("tegj", "L");
    }

    @Test
    public void shouldFindNumeral() {
        Assert.assertTrue(numeral.containsKey("glob"));
    }

    @Test
    public void shouldNotFindNumeral() {
        Assert.assertTrue(!numeral.containsKey("xxxx"));
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotAddInvalidNumeral() {
        numeral.add(null, "L");
    }

    @Test
    public void shouldGetRomanNumeral() {
        Assert.assertEquals("V", numeral.getValue("prok"));
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailToGetNonExistingNumeral() {
        numeral.getValue("xxxx");
    }
}
