package com.virtusa.guide.to.galaxy.parser;

import org.junit.Assert;
import org.junit.Test;

public class TestStatementParserFactory {

    @Test
    public void shouldGetParser() {
        Assert.assertTrue(StatementParserFactory.INSTANCE.getParser(ParserType.ETNumeral)
                .equals(ETNumeralStatementParser.INSTANCE));

        Assert.assertTrue(StatementParserFactory.INSTANCE.getParser(ParserType.ProductInfo)
                .equals(ProductInfoStatementParser.INSTANCE));
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailToGetParser() {
        StatementParserFactory.INSTANCE.getParser(null);
    }
}
