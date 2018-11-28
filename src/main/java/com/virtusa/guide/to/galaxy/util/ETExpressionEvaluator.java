package com.virtusa.guide.to.galaxy.util;

import com.virtusa.guide.to.galaxy.mapper.ETNumeral;

import org.apache.commons.lang3.StringUtils;

public enum ETExpressionEvaluator implements ExpressionEvaluator {
    INSTANCE;

    private ETNumeral etNumeral = ETNumeral.INSTANCE;

    @Override
    public double evaluate(String expr) {
        if (StringUtils.isBlank(expr))
            throw new RuntimeException("Cannot evaluate blank expression.");

        StringBuilder romanNumeralExpr = new StringBuilder();
        String[] numerals = expr.split(" ");

        for (String numeral : numerals) {
            romanNumeralExpr.append(etNumeral.getValue(numeral));
        }

        return RomanExpressionEvaluator.INSTANCE.evaluate(romanNumeralExpr.toString());
    }

}
