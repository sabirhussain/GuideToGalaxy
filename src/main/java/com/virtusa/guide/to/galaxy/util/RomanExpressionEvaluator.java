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
                ex.append(last);
                continue;
            }

            addExpression(last, current, ex);
            last = current;
        }

        return ex.toString();
    }

    private void addExpression(int lastNum, int currentNum, StringBuilder ex) {
        if (lastNum < currentNum) {
            int lastNumLen = String.valueOf(lastNum).length();
            ex.delete(ex.length() - lastNumLen, ex.length());
            ex.append(currentNum).append("-").append(lastNum);
            return;
        }

        ex.append("+").append(currentNum);
    }
}
