package com.virtusa.guide.to.galaxy.parser;

import com.virtusa.guide.to.galaxy.mapper.ETNumeral;
import com.virtusa.guide.to.galaxy.mapper.RomanNumeral;

public enum ETNumeralStatementParser implements StatementParser {
    INSTANCE;
    
    @Override
    public boolean parse(String statement) {
        String[] parts = statement.split(" ");
        String romanNumeral = parts[parts.length - 1].trim();

        if (!RomanNumeral.INSTANCE.containsKey(romanNumeral)) {
            return false;
        }

        ETNumeral.INSTANCE.add(parts[0].trim(), parts[parts.length - 1].trim());
        return true;
    }

}
