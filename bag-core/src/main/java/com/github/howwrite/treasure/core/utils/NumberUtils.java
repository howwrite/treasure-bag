package com.github.howwrite.treasure.core.utils;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

/**
 * BigDecimal 相关的常用工具, 用于统一各处的数值处理方式。
 */
public final class NumberUtils {

    /**
     * 10^2, 例如百分比换算或分转元。
     */
    public static final BigDecimal HUNDRED = new BigDecimal("100");

    /**
     * 10^3, 常用于“千”的数量级。
     */
    public static final BigDecimal THOUSAND = new BigDecimal("1000");

    /** 10^4, 对应“万”(\u4e07) 的数量级。 */
    public static final BigDecimal TEN_THOUSAND = new BigDecimal("10000");

    /**
     * 10^8, 对应“亿”(\u4ebf) 的数量级。
     */
    public static final BigDecimal ONE_HUNDRED_MILLION = new BigDecimal("100000000");

    private NumberUtils() {
    }

    /**
     * 将小数 (例:0.56) 转为百分比字符串 (例:56%), 默认保留两位小数。
     */
    public static String toPercentageString(@Nonnull BigDecimal decimal) {
        return toPercentageString(decimal, 2);
    }

    /**
     * 将小数转为百分比字符串, 支持自定义小数位数。
     */
    public static String toPercentageString(@Nonnull BigDecimal decimal, int scale) {
        BigDecimal value = defaultIfNull(decimal)
                .multiply(HUNDRED)
                .setScale(scale, RoundingMode.HALF_UP)
                .stripTrailingZeros();
        return value.toPlainString() + "%";
    }

    /**
     * 兼容旧接口的别名方法。
     */
    public static String convertPercentageStr(@Nonnull BigDecimal decimal) {
        return toPercentageString(decimal);
    }

    /**
     * 将 null 视为 0, 简化后续计算。
     */
    public static BigDecimal defaultIfNull(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    /**
     * 求和时忽略 null, 将其视为 0。
     */
    public static BigDecimal sum(BigDecimal... numbers) {
        BigDecimal result = BigDecimal.ZERO;
        if (numbers == null) {
            return result;
        }
        for (BigDecimal number : numbers) {
            if (number != null) {
                result = result.add(number);
            }
        }
        return result;
    }

    /**
     * 处理集合版本的求和, 同样忽略 null。
     */
    public static BigDecimal sum(Collection<BigDecimal> numbers) {
        BigDecimal result = BigDecimal.ZERO;
        if (numbers == null) {
            return result;
        }
        for (BigDecimal number : numbers) {
            if (number != null) {
                result = result.add(number);
            }
        }
        return result;
    }

    /**
     * 安全除法, 当除数为 null 或 0 时返回 0。
     */
    public static BigDecimal safeDivide(BigDecimal dividend, BigDecimal divisor, int scale) {
        return safeDivide(dividend, divisor, scale, RoundingMode.HALF_UP);
    }

    /**
     * 安全除法(可指定舍入方式), 除数为 null 或 0 时返回 0。
     */
    public static BigDecimal safeDivide(BigDecimal dividend, BigDecimal divisor, int scale, RoundingMode roundingMode) {
        if (dividend == null || divisor == null || isZero(divisor)) {
            return BigDecimal.ZERO;
        }
        return dividend.divide(divisor, scale, roundingMode);
    }

    /**
     * 判断数值是否为 0 (null 视为 0)。
     */
    public static boolean isZero(BigDecimal number) {
        return defaultIfNull(number).compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * 判断数值是否大于 0 (null 视为 0)。
     */
    public static boolean isPositive(BigDecimal number) {
        return defaultIfNull(number).compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 判断数值是否小于 0 (null 视为 0)。
     */
    public static boolean isNegative(BigDecimal number) {
        return defaultIfNull(number).compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * null 安全的 compareTo, null 视为 0。
     */
    public static int compare(BigDecimal left, BigDecimal right) {
        return defaultIfNull(left).compareTo(defaultIfNull(right));
    }

    /**
     * 解析包含千位分隔符、百分号、千分号或 K/M/B/万/亿 等数量级后缀的字符串。
     */
    public static BigDecimal buildBigDecimal(@Nonnull String numberStr) {
        if (StringUtils.isBlank(numberStr)) {
            return BigDecimal.ZERO;
        }

        String sanitized = StringUtils.deleteWhitespace(numberStr)
                .replace(",", "")
                .replace("_", "")
                .trim();
        if (StringUtils.isBlank(sanitized)) {
            return BigDecimal.ZERO;
        }

        BigDecimal coefficient = BigDecimal.ONE;

        if (sanitized.endsWith("%")) {
            coefficient = coefficient.multiply(BigDecimal.ONE.divide(HUNDRED, 10, RoundingMode.HALF_UP));
            sanitized = sanitized.substring(0, sanitized.length() - 1);
        } else if (sanitized.endsWith("\u2030")) {
            coefficient = coefficient.multiply(BigDecimal.ONE.divide(THOUSAND, 10, RoundingMode.HALF_UP));
            sanitized = sanitized.substring(0, sanitized.length() - 1);
        }

        String upperCase = sanitized.toUpperCase();
        if (upperCase.endsWith("K")) {
            coefficient = coefficient.multiply(THOUSAND);
            sanitized = sanitized.substring(0, sanitized.length() - 1);
        } else if (upperCase.endsWith("M")) {
            coefficient = coefficient.multiply(THOUSAND).multiply(THOUSAND);
            sanitized = sanitized.substring(0, sanitized.length() - 1);
        } else if (upperCase.endsWith("B")) {
            coefficient = coefficient.multiply(THOUSAND).multiply(THOUSAND).multiply(THOUSAND);
            sanitized = sanitized.substring(0, sanitized.length() - 1);
        }

        if (sanitized.contains("\u4e07")) { // Chinese 'wan' (ten-thousand)
            coefficient = coefficient.multiply(TEN_THOUSAND);
            sanitized = sanitized.replace("\u4e07", "");
        }
        if (sanitized.contains("\u4ebf")) { // Chinese 'yi' (hundred-million)
            coefficient = coefficient.multiply(ONE_HUNDRED_MILLION);
            sanitized = sanitized.replace("\u4ebf", "");
        }

        if (StringUtils.isBlank(sanitized)) {
            return BigDecimal.ZERO;
        }

        return coefficient.multiply(new BigDecimal(sanitized));
    }
}
