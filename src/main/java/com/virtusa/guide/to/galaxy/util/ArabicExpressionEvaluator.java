package com.virtusa.guide.to.galaxy.util;

import org.apache.commons.lang3.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public enum ArabicExpressionEvaluator implements ExpressionEvaluator {
    INSTANCE;

    private ScriptEngine engine;

    private ArabicExpressionEvaluator() {
        ScriptEngineManager mgr = new ScriptEngineManager();
        this.engine = mgr.getEngineByName("JavaScript");
    }

    @Override
    public double evaluate(String expr) {
        if (StringUtils.isBlank(expr))
            throw new RuntimeException("Cannot evaluate blank expression.");

        try {
            return Double.parseDouble(engine.eval(expr).toString());
        } catch (NumberFormatException | ScriptException e) {
            throw new RuntimeException(e);
        }
    }

}
