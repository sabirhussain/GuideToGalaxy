package com.virtusa.guide.to.galaxy.validator;

import com.virtusa.guide.to.galaxy.mapper.ETNumeral;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ETExpressionValidator implements ExpressionValidator {
    INSTANCE;

    ETNumeral etNumeral = ETNumeral.INSTANCE;

    @Override
    public List<String> validate(String expr) {
        List<String> errors = new ArrayList<>();

        if (StringUtils.isBlank(expr))
            errors.add("Blank Expression.");

        String[] numerals = expr.split(" ");

        Arrays.stream(numerals).forEach(n -> {
            if (!etNumeral.containsKey(n)) {
                errors.add(n + " is not an ET Numeral.");
            }
        });

        return errors;
    }

}
