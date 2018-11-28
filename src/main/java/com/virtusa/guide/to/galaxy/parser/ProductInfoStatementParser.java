package com.virtusa.guide.to.galaxy.parser;

import com.virtusa.guide.to.galaxy.mapper.ETNumeral;
import com.virtusa.guide.to.galaxy.util.ETExpressionEvaluator;
import com.virtusa.guide.to.galaxy.vo.ProductInfo;
import com.virtusa.guide.to.galaxy.vo.ProductUnitPriceStore;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public enum ProductInfoStatementParser implements StatementParser {
    INSTANCE;

    @Override
    public boolean parse(String statement) {
        String[] parts = statement.split("is");
        Optional<ProductInfo> optional = getProductInfo(parts[0]);

        if (optional.isPresent()) {
            ProductInfo info = optional.get();
            int credits = Integer.parseInt(parts[1].replace("Credits", "").trim());
            storeProductUnitPrice(info.getName(), info.getQty(), credits);
            return true;
        }

        return false;
    }

    private Optional<ProductInfo> getProductInfo(String productPart) {
        String[] parts = productPart.split(" ");
        StringBuilder etExpr = new StringBuilder();
        String product = null;

        for (String token : parts) {
            if (StringUtils.isBlank(token))
                continue;

            if (etExpr.length() == 0 && !ETNumeral.INSTANCE.containsKey(token)) {
                return Optional.empty();
            }

            if (product != null) {
                return Optional.empty();
            }

            if (!ETNumeral.INSTANCE.containsKey(token)) {
                product = token;
                continue;
            }

            etExpr.append(token).append(" ");
        }

        return Optional.of(new ProductInfo(product, etExpr.toString()));
    }

    private static void storeProductUnitPrice(String product, String qty, int totalPrice) {
        double d = ETExpressionEvaluator.INSTANCE.evaluate(qty);
        ProductUnitPriceStore.INSTANCE.add(product, totalPrice / d);
    }
}
