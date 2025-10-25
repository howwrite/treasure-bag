package com.github.howwrite.treasure.core.utils;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 金额相关工具类, 统一元/分/厘换算与常用货币计算逻辑。
 */
public final class PriceUtils {

    private static final int DEFAULT_SCALE = 2;

    private static final int LI_SCALE = 3;

    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_UP;

    private PriceUtils() {
    }

    /**
     * 保留旧方法名以兼容历史调用, 建议使用 {@link #fenToYuanString(long)}。
     */
    public static String fen2Yuan(long fen) {
        return fenToYuanString(fen);
    }

    /**
     * long 类型分金额转为元(保留 2 位小数)。
     */
    public static BigDecimal fenToYuan(long fen) {
        return fenToYuan(BigDecimal.valueOf(fen));
    }

    /**
     * BigDecimal 类型分金额转为元(保留 2 位小数)。
     */
    public static BigDecimal fenToYuan(@Nullable BigDecimal fen) {
        if (fen == null) {
            return BigDecimal.ZERO.setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
        }
        return fen.divide(NumberUtils.HUNDRED, DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    /**
     * long 类型分金额转为字符串形式的元金额, 禁用科学计数法。
     */
    public static String fenToYuanString(long fen) {
        return fenToYuan(fen).toPlainString();
    }

    /**
     * BigDecimal 类型分金额转为字符串形式的元金额。
     */
    public static String fenToYuanString(@Nullable BigDecimal fen) {
        return fenToYuan(fen).toPlainString();
    }

    /**
     * long 类型厘金额转为元(保留 3 位小数)。
     */
    public static BigDecimal liToYuan(long li) {
        return liToYuan(BigDecimal.valueOf(li));
    }

    /**
     * BigDecimal 类型厘金额转为元(保留 3 位小数)。
     */
    public static BigDecimal liToYuan(@Nullable BigDecimal li) {
        if (li == null) {
            return BigDecimal.ZERO.setScale(LI_SCALE, DEFAULT_ROUNDING);
        }
        return li.divide(NumberUtils.THOUSAND, LI_SCALE, DEFAULT_ROUNDING);
    }

    /**
     * long 类型厘金额转为字符串形式的元金额。
     */
    public static String liToYuanString(long li) {
        return liToYuan(li).toPlainString();
    }

    /**
     * BigDecimal 类型厘金额转为字符串形式的元金额。
     */
    public static String liToYuanString(@Nullable BigDecimal li) {
        return liToYuan(li).toPlainString();
    }

    /**
     * 元金额转为分(long), 使用默认舍入规则。
     */
    public static long yuanToFen(@Nullable BigDecimal yuan) {
        return yuanToFen(yuan, DEFAULT_ROUNDING);
    }

    /**
     * 元金额转为分(long), 可指定舍入规则。
     */
    public static long yuanToFen(@Nullable BigDecimal yuan, RoundingMode roundingMode) {
        if (yuan == null) {
            return 0L;
        }
        BigDecimal normalized = NumberUtils.defaultIfNull(yuan).setScale(DEFAULT_SCALE, roundingMode);
        return normalized.multiply(NumberUtils.HUNDRED).longValue();
    }

    /**
     * 元金额转为厘(long), 使用默认舍入规则。
     */
    public static long yuanToLi(@Nullable BigDecimal yuan) {
        return yuanToLi(yuan, DEFAULT_ROUNDING);
    }

    /**
     * 元金额转为厘(long), 可指定舍入规则。
     */
    public static long yuanToLi(@Nullable BigDecimal yuan, RoundingMode roundingMode) {
        if (yuan == null) {
            return 0L;
        }
        BigDecimal normalized = NumberUtils.defaultIfNull(yuan).setScale(LI_SCALE, roundingMode);
        return normalized.multiply(NumberUtils.THOUSAND).longValue();
    }

    /**
     * 分金额转为厘(long), 直接放大 10 倍。
     */
    public static long fenToLi(long fen) {
        return fen * 10L;
    }

    /**
     * BigDecimal 分金额转为厘(BigDecimal), 保留原始精度。
     */
    public static BigDecimal fenToLi(@Nullable BigDecimal fen) {
        return NumberUtils.defaultIfNull(fen).multiply(BigDecimal.TEN);
    }

    /**
     * 厘金额转为分(long), 默认四舍五入。
     */
    public static long liToFen(long li) {
        return liToFen(BigDecimal.valueOf(li), DEFAULT_ROUNDING);
    }

    /**
     * 厘金额转为分(long), 可指定舍入规则。
     */
    public static long liToFen(long li, RoundingMode roundingMode) {
        return liToFen(BigDecimal.valueOf(li), roundingMode);
    }

    /**
     * 厘金额转为分(long), 可指定舍入规则。
     */
    public static long liToFen(@Nullable BigDecimal li) {
        return liToFen(li, DEFAULT_ROUNDING);
    }

    /**
     * 厘金额转为分(long), 可指定舍入规则。
     */
    public static long liToFen(@Nullable BigDecimal li, RoundingMode roundingMode) {
        if (li == null) {
            return 0L;
        }
        return li.divide(BigDecimal.TEN, 0, roundingMode).longValue();
    }

    /**
     * 厘金额转为分(BigDecimal), 默认保留 1 位小数。
     */
    public static BigDecimal liToFenDecimal(@Nullable BigDecimal li) {
        BigDecimal safeLi = NumberUtils.defaultIfNull(li);
        return safeLi.divide(BigDecimal.TEN, 1, DEFAULT_ROUNDING);
    }

    /**
     * 将任意金额标准化为默认精度(2 位小数)。
     */
    public static BigDecimal normalizeCurrency(@Nullable BigDecimal amount) {
        return normalizeCurrency(amount, DEFAULT_ROUNDING);
    }

    /**
     * 将任意金额标准化为默认精度(2 位小数), 可自定义舍入。
     */
    public static BigDecimal normalizeCurrency(@Nullable BigDecimal amount, RoundingMode roundingMode) {
        if (amount == null) {
            return BigDecimal.ZERO.setScale(DEFAULT_SCALE, roundingMode);
        }
        return amount.setScale(DEFAULT_SCALE, roundingMode);
    }

    /**
     * 安全求和多个金额(null 视为 0)并标准化精度。
     */
    public static BigDecimal addAmounts(BigDecimal... amounts) {
        return normalizeCurrency(NumberUtils.sum(amounts));
    }

    /**
     * 使用默认货币精度格式化金额。
     */
    public static String formatCurrency(BigDecimal amount) {
        return normalizeCurrency(amount).toPlainString();
    }

    /**
     * 使用自定义小数位数格式化金额。
     */
    public static String formatCurrency(BigDecimal amount, int scale) {
        BigDecimal normalized = NumberUtils.defaultIfNull(amount).setScale(scale, DEFAULT_ROUNDING);
        return normalized.toPlainString();
    }

    /**
     * 金额相减, null 视为 0, 并标准化结果。
     */
    public static BigDecimal subtract(BigDecimal minuend, BigDecimal subtrahend) {
        BigDecimal safeMinuend = NumberUtils.defaultIfNull(minuend);
        BigDecimal safeSubtrahend = NumberUtils.defaultIfNull(subtrahend);
        return normalizeCurrency(safeMinuend.subtract(safeSubtrahend));
    }

    /**
     * 计算金额按百分比(0-100)占比后的数值。
     */
    public static BigDecimal percentageOf(BigDecimal amount, BigDecimal percentage) {
        BigDecimal safeAmount = NumberUtils.defaultIfNull(amount);
        BigDecimal safePercentage = NumberUtils.defaultIfNull(percentage);
        BigDecimal share = NumberUtils.safeDivide(safePercentage, NumberUtils.HUNDRED, DEFAULT_SCALE + 2);
        return normalizeCurrency(safeAmount.multiply(share));
    }

    /**
     * 按折扣率(0-100)折算金额并标准化。
     */
    public static BigDecimal applyDiscount(BigDecimal amount, BigDecimal discountRate) {
        BigDecimal safeAmount = NumberUtils.defaultIfNull(amount);
        BigDecimal safeDiscountRate = NumberUtils.defaultIfNull(discountRate);
        BigDecimal discountFraction = NumberUtils.safeDivide(safeDiscountRate, NumberUtils.HUNDRED, DEFAULT_SCALE + 2);
        BigDecimal multiplier = BigDecimal.ONE.subtract(discountFraction);
        return normalizeCurrency(safeAmount.multiply(multiplier));
    }

    /**
     * 通过统一到分的精度比较金额是否相等。
     */
    public static boolean equalsInFen(BigDecimal left, BigDecimal right) {
        BigDecimal safeLeft = normalizeCurrency(left);
        BigDecimal safeRight = normalizeCurrency(right);
        return safeLeft.compareTo(safeRight) == 0;
    }

    /**
     * 判断标准化后的金额是否为正。
     */
    public static boolean isPositiveAmount(BigDecimal amount) {
        return NumberUtils.isPositive(normalizeCurrency(amount));
    }
}
