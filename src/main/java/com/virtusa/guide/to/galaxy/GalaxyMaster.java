package com.virtusa.guide.to.galaxy;

import com.virtusa.guide.to.galaxy.parser.ParserType;
import com.virtusa.guide.to.galaxy.parser.StatementParserFactory;
import com.virtusa.guide.to.galaxy.util.ETExpressionEvaluator;
import com.virtusa.guide.to.galaxy.validator.ETExpressionValidator;
import com.virtusa.guide.to.galaxy.vo.ProductUnitPriceStore;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Intelligent system that will answer our questions.
 * 
 * @author sabir
 *
 */
public enum GalaxyMaster {
    INSTANCE;
    private static final String ET_NUMERAL_DECLARATION_MATCHER = "[a-zA-Z]{4}[ ]+is[ ]+[IVXLCDM]";
    private static final String CREDITS_DECLARATION_MATCHER = "([a-zA-Z]{4,6}[ ]+)+is[ ]+[0-9]{1,6}[ ]+Credits";

    private ProductUnitPriceStore productUnitPriceStore = ProductUnitPriceStore.INSTANCE;

    /**
     * This will update knowledge base.
     * @param statement the knowledge statement.
     * @param unknownStatements the collection to update if statement was not useful.
     * @return true if knowledge base updated.
     */
    public boolean feedKnowledge(String statement, List<String> unknownStatements) {
        if (StringUtils.isBlank(statement))
            return false;

        if (statement.matches(ET_NUMERAL_DECLARATION_MATCHER)) {
            return parseETNumeralStatement(statement, unknownStatements);
        }

        if (statement.matches(CREDITS_DECLARATION_MATCHER)) {
            return parserProductInfoStatement(statement, unknownStatements);
        }

        return false;
    }

    /**
     * This will answer questions.
     * 
     * @param questions the questions to answer.
     * @return unresolved questions.
     */
    public List<String> answerQuestions(List<String> questions) {
        if (questions == null || questions.isEmpty())
            return Collections.emptyList();

        List<String> unknownStatements = new ArrayList<>();

        questions.forEach(q -> {
            int idx = q.indexOf("is ");

            if (idx == -1) {
                unknownStatements.add(q);
                return;
            }

            if (q.contains("Credits")) {
                answerCreditQuestion(q, idx, unknownStatements);
                return;
            }

            resolveETNumeralExpression(q, idx, unknownStatements);
        });

        return unknownStatements;
    }

    private void answerCreditQuestion(String question, int isIndex, List<String> unknownStatements) {

        String expr = question.substring(isIndex + 3).replace('?', ' ').trim();

        String[] exprs = expr.split(" ");
        String product = exprs[exprs.length - 1].trim();

        if (!productUnitPriceStore.exists(product)) {
            unknownStatements.add(question);
            return;
        }

        String etExpr = expr.replace(product, "");
        List<String> errors = ETExpressionValidator.INSTANCE.validate(etExpr);

        if (!errors.isEmpty()) {
            unknownStatements.add(question);
            System.out.println(errors);
            return;
        }

        double multiplier = ETExpressionEvaluator.INSTANCE.evaluate(etExpr);
        double price = productUnitPriceStore.getUnitPrice(product) * multiplier;
        System.out.println(expr + " is " + price + " Credits");
    }

    private void resolveETNumeralExpression(String question, int isIndex, List<String> unknownStatements) {
        String expr = question.substring(isIndex + 3).replace('?', ' ').trim();
        List<String> errors = ETExpressionValidator.INSTANCE.validate(expr);

        if (!errors.isEmpty()) {
            unknownStatements.add(question);
            System.out.println(errors);
            return;
        }

        System.out.println(expr + " is " + ETExpressionEvaluator.INSTANCE.evaluate(expr));
    }

    private boolean parseETNumeralStatement(String line, List<String> unknownStatements) {
        if (StatementParserFactory.INSTANCE.getParser(ParserType.ETNumeral).parse(line))
            return true;

        unknownStatements.add(line);
        return false;
    }

    private boolean parserProductInfoStatement(String line, List<String> unknownStatements) {
        if (StatementParserFactory.INSTANCE.getParser(ParserType.ProductInfo).parse(line))
            return true;

        unknownStatements.add(line);
        return false;
    }
}
