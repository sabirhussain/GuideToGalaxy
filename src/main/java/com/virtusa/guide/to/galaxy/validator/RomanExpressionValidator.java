package com.virtusa.guide.to.galaxy.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum RomanExpressionValidator implements ExpressionValidator {
    INSTANCE;

    private List<ExpressionValidator> validators = new ArrayList<>();

    private RomanExpressionValidator() {
        validators.add(new ExpressionValidator() {

            @Override
            public List<String> validate(String expr) {
                if (StringUtils.isBlank(expr))
                    return Arrays.asList("Blank Expression.");

                return Collections.emptyList();
            }
        });

        validators.add(new ExpressionValidator() {

            @Override
            public List<String> validate(String expr) {
                List<String> errors = new ArrayList<>();

                if (expr.contains("IIII") || expr.contains("XXXX") || expr.contains("CCCC") || expr.contains("MMMM"))
                    errors.add("IIII | XXXX | CCCC | MMMM not allowed");

                if (expr.contains("DD") || expr.contains("LL") || expr.contains("VV"))
                    errors.add("DD | LL | VV not allowed.");

                return errors;
            }
        });

        validators.add(new ExpressionValidator() {

            @Override
            public List<String> validate(String expr) {
                List<String> errors = new ArrayList<>();
                int idx = expr.indexOf("I");

                if (idx != -1 && idx + 1 < expr.length() && !expr.substring(idx + 1, idx + 2).matches("[IVX]"))
                    errors.add("I must be followed by V or X.");

                idx = expr.indexOf("X");

                if (idx != -1 && idx + 1 < expr.length() && !expr.substring(idx + 1, idx + 2).matches("[XLC]"))
                    errors.add("X must be followed by L or C.");

                idx = expr.indexOf("C");

                if (idx != -1 && idx + 1 < expr.length() && !expr.substring(idx + 1, idx + 2).matches("[CDM]"))
                    errors.add("C must be followed by D or M.");

                return errors;
            }
        });

        validators.add(new ExpressionValidator() {

            @Override
            public List<String> validate(String expr) {
                List<String> errors = new ArrayList<>();
                int idx = expr.indexOf("V");

                if (idx != -1 && idx + 1 < expr.length() && !expr.substring(idx + 1, idx + 2).matches("[I]"))
                    errors.add("V must be followed by I.");

                idx = expr.indexOf("L");

                if (idx != -1 && idx + 1 < expr.length() && !expr.substring(idx + 1, idx + 2).matches("[IVX]"))
                    errors.add("L must be followed by I, V or X.");

                idx = expr.indexOf("D");

                if (idx != -1 && idx + 1 < expr.length() && !expr.substring(idx + 1, idx + 2).matches("[IVXLC]"))
                    errors.add("D must be followed by I, V, X, L or C.");

                return errors;
            }
        });

    }

    @Override
    public List<String> validate(String expr) {
        List<String> errors = new ArrayList<>();
        validators.forEach(v -> errors.addAll(v.validate(expr)));
        return errors;
    }
}
