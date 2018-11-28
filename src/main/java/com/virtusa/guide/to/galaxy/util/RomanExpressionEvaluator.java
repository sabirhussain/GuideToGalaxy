package com.virtusa.guide.to.galaxy.util;

import com.virtusa.guide.to.galaxy.mapper.RomanNumeral;

import org.apache.commons.lang3.StringUtils;

public enum RomanExpressionEvaluator implements ExpressionEvaluator {
    INSTANCE;
    private RomanNumeral numeral = RomanNumeral.INSTANCE;

    @Override
    public double evaluate(String expr) {
        if (StringUtils.isBlank(expr))
            throw new RuntimeException("Cannot evaluate blank expression.");

        String arabicExpr = toArabic(expr);
        return ArabicExpressionEvaluator.INSTANCE.evaluate(arabicExpr);
    }

    private String toArabic(String expr) {
        StringBuilder ex = new StringBuilder();
        int last = 0;

        for (char c : expr.toCharArray()) {
            int current = numeral.getValue(String.valueOf(c));

            if (last == 0) {
                last = current;
                continue;
            }

            ex.append(getExpression(last, current));
            last = current;
        }

        if (ex.length() == 0)
            ex.append(last);

        return ex.toString();
    }

    private String getExpression(int lastNum, int currentNum) {
        if (lastNum < currentNum)
            return currentNum + "-" + lastNum;

        return lastNum + "+" + currentNum;
    }
}
