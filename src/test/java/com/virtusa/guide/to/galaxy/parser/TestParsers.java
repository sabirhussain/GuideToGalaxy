package com.virtusa.guide.to.galaxy.parser;

import com.virtusa.guide.to.galaxy.mapper.ETNumeral;
import com.virtusa.guide.to.galaxy.vo.ProductUnitPriceStore;

import org.junit.Assert;
import org.junit.Test;

public class TestParsers {

    @Test
    public void shouldParseETNumeralStatement() {
        Assert.assertTrue(ETNumeralStatementParser.INSTANCE.parse("blog is I"));
        Assert.assertTrue(ETNumeral.INSTANCE.containsKey("blog"));
    }

    @Test
    public void shouldFailToParseETNumeralStatement() {
        Assert.assertTrue(!ETNumeralStatementParser.INSTANCE.parse("yuyu is "));
        Assert.assertTrue(!ETNumeral.INSTANCE.containsKey("yuyu"));
    }

    @Test
    public void shouldParseProductInfoStatement() {
        ETNumeral.INSTANCE.add("blog", "I");
        Assert.assertTrue(ProductInfoStatementParser.INSTANCE.parse("blog Silver is 24 Credits"));
        Assert.assertEquals(24d, ProductUnitPriceStore.INSTANCE.getUnitPrice("Silver"), 0);
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailToParseProductInfoStatement() {
        ETNumeral.INSTANCE.add("blog", "I");
        Assert.assertTrue(!ProductInfoStatementParser.INSTANCE.parse("blog Gold is"));
        ProductUnitPriceStore.INSTANCE.getUnitPrice("Gold");
    }
}
