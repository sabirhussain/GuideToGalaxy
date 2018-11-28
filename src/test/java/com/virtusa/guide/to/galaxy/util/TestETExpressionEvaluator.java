package com.virtusa.guide.to.galaxy.util;

import org.junit.Test;

public class TestETExpressionEvaluator {

    @Test(expected = RuntimeException.class)
    public void shouldFailToEvaluateNull() {
        ETExpressionEvaluator.INSTANCE.evaluate(null);
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailToEvaluateEmpty() {
        ETExpressionEvaluator.INSTANCE.evaluate("");
    }
}
