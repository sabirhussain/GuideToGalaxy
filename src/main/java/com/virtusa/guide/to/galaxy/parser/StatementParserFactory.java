package com.virtusa.guide.to.galaxy.parser;

public enum StatementParserFactory {
    INSTANCE;

    public StatementParser getParser(ParserType type) {
        if (ParserType.ETNumeral == type)
            return ETNumeralStatementParser.INSTANCE;

        if (ParserType.ProductInfo == type)
            return ProductInfoStatementParser.INSTANCE;

        throw new RuntimeException("Parser not defined for " + type);
    }
}
