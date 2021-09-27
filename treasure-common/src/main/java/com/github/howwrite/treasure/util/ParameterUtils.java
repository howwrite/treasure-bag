package com.github.howwrite.treasure.util;

import java.util.Collection;
import java.util.Objects;

/**
 * @author howwrite
 * @date 2020/10/7 9:44 下午
 */
public class ParameterUtils {
    public static void notNull(Object obj, String message) {
        if (Objects.isNull(obj)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Collection<?> collection, String message) {
        notNull(collection, message);
        if (collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
}
