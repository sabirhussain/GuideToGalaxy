package com.virtusa.guide.to.galaxy.util;

import org.junit.Assert;
import org.junit.Test;

public class TestRomanExpressionEvaluator {

    @Test(expected = RuntimeException.class)
    public void shouldFailToEvaluateNull() {
        RomanExpressionEvaluator.INSTANCE.evaluate(null);
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailToEvaluateEmpty() {
        RomanExpressionEvaluator.INSTANCE.evaluate("");
    }

    @Test
    public void shouldGetArabicNumeralExpression() {
        Assert.assertEquals(14, RomanExpressionEvaluator.INSTANCE.evaluate("XIV"), 0);
    }
}
