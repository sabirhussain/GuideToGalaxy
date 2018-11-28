package com.virtusa.guide.to.galaxy.util;

import org.junit.Assert;
import org.junit.Test;

public class TestArabicExpressionEvaluator {

    @Test
    public void shouldEvaluateExpression() {
        double result = ArabicExpressionEvaluator.INSTANCE.evaluate("5-1");
        Assert.assertEquals(4, result, 0);
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailForInvalidNumber() {
        ArabicExpressionEvaluator.INSTANCE.evaluate("ABC");
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailToEvaluateExpression() {
        ArabicExpressionEvaluator.INSTANCE.evaluate(null);
    }
}
