package com.github.howwrite.treasure.core.utils;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {
    public static final BigDecimal HUNDRED = new BigDecimal("100");

    public static final BigDecimal TEN_THOUSAND = new BigDecimal("10000");

    public static String convertPercentageStr(@Nonnull BigDecimal bigDecimal) {
        return bigDecimal.multiply(HUNDRED).toPlainString() + "%";
    }

    public static BigDecimal buildBigDecimal(@Nonnull String numberStr) {
        if (StringUtils.isBlank(numberStr)) {
            return BigDecimal.ZERO;
        }
        numberStr = numberStr.replace(",", "").trim();
        BigDecimal coefficient = BigDecimal.ONE;
        if (numberStr.contains("%")) {
            coefficient = BigDecimal.ONE.divide(HUNDRED, 5, RoundingMode.HALF_UP);
            numberStr = numberStr.replace("%", "");
        } else if (numberStr.contains("万")) {
            coefficient = TEN_THOUSAND;
            numberStr = numberStr.replace("万", "");
        }
        return coefficient.multiply(new BigDecimal(numberStr));
    }
}
