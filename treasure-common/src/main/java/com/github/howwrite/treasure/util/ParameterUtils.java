package com.github.howwrite.treasure.util;

import cn.hutool.core.util.StrUtil;

import java.util.Collection;
import java.util.Objects;

/**
 * @author howwrite
 * @date 2020/10/7 9:44 下午
 */
public class ParameterUtils {
    public static void notNull(String message, Object obj) {
        if (Objects.isNull(obj)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(String message, Collection<?> collection) {
        notNull(message, collection);
        if (collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notBlank(String message, CharSequence str) {
        if (StrUtil.isBlank(str)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void anyNotEmpty(String message, CharSequence... charSequences) {
        if (charSequences == null || charSequences.length == 0) {
            throw new IllegalArgumentException(message);
        }
        for (CharSequence charSequence : charSequences) {
            if (StrUtil.isNotBlank(charSequence)) {
                return;
            }
        }
        throw new IllegalArgumentException(message);
    }
}
