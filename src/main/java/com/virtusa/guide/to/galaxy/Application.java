package com.virtusa.guide.to.galaxy;

import com.virtusa.guide.to.galaxy.parser.ParserType;
import com.virtusa.guide.to.galaxy.parser.StatementParserFactory;
import com.virtusa.guide.to.galaxy.util.ETExpressionEvaluator;
import com.virtusa.guide.to.galaxy.validator.ETExpressionValidator;
import com.virtusa.guide.to.galaxy.vo.ProductUnitPriceStore;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {
    private static final String ET_NUMERAL_DECLARATION_MATCHER = "[a-zA-Z]{4}[ ]+is[ ]+[IVXLCDM]";
    private static final String CREDITS_DECLARATION_MATCHER = "([a-zA-Z]{4,6}[ ]+)+is[ ]+[0-9]{1,6}[ ]+Credits";

    private static List<String> unknownStatements = new ArrayList<>();
    private static ProductUnitPriceStore productUnitPriceStore = ProductUnitPriceStore.INSTANCE;

    public static void main(String[] args) {
        List<String> questions = new ArrayList<>();

        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Start Input (To end type DONE)");

            for (String line = sc.nextLine(); !"DONE".equals(line); line = sc.nextLine()) {
                if (StringUtils.isBlank(line))
                    continue;

                if (line.matches(ET_NUMERAL_DECLARATION_MATCHER)) {
                    parseETNumeralStatement(line);
                    continue;
                }

                if (line.matches(CREDITS_DECLARATION_MATCHER)) {
                    parserProductInfoStatement(line);
                    continue;
                }

                if (line.contains("?") && line.contains("is")) {
                    questions.add(line);
                    continue;
                }

                unknownStatements.add(line);
            }
        }

        System.out.println("======== Output ========");
        answerQuestions(questions);
        unknownStatements.forEach(s -> {
            System.out.println("[" + s + "] I have not idea what you talking about.");
        });
    }

    private static void parseETNumeralStatement(String line) {
        if (!StatementParserFactory.INSTANCE.getParser(ParserType.ETNumeral).parse(line))
            unknownStatements.add(line);
    }

    private static void parserProductInfoStatement(String line) {
        if (!StatementParserFactory.INSTANCE.getParser(ParserType.ProductInfo).parse(line))
            unknownStatements.add(line);
    }

    private static void answerQuestions(List<String> questions) {
        questions.forEach(q -> {
            int idx = q.indexOf("is ");

            if (idx == -1) {
                unknownStatements.add(q);
                return;
            }

            if (q.contains("Credits")) {
                answerCreditQuestion(q, idx);
                return;
            }

            resolveETNumeralExpression(q, idx);
        });
    }

    private static void resolveETNumeralExpression(String question, int isIndex) {
        String expr = question.substring(isIndex + 3).replace('?', ' ').trim();
        List<String> errors = ETExpressionValidator.INSTANCE.validate(expr);

        if (!errors.isEmpty()) {
            unknownStatements.add(question);
            System.out.println(errors);
            return;
        }

        System.out.println(expr + " is " + ETExpressionEvaluator.INSTANCE.evaluate(expr));
    }

    private static void answerCreditQuestion(String question, int isIndex) {

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
}
