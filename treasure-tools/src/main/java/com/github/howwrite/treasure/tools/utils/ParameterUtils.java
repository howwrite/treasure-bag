package com.github.howwrite.treasure.tools.utils;

import cn.hutool.core.util.StrUtil;

import java.util.Collection;
import java.util.Objects;

/**
 * @author howwrite
 * @date 2020/10/7 9:44 下午
 */
public class ParameterUtils {
    public static CheckBox notNull(String message, Object obj) {
        return new CheckBox().notNull(message, obj);
    }

    public static CheckBox notEmpty(String message, Collection<?> collection) {
        return new CheckBox().notEmpty(message, collection);
    }

    public static CheckBox notBlank(String message, CharSequence str) {
        return new CheckBox().anyNotNull(message, str);
    }

    public static CheckBox anyNotEmpty(String message, CharSequence... charSequences) {
        return new CheckBox().anyNotEmpty(message, charSequences);
    }

    public static CheckBox anyNotNull(String message, Object... objs) {
        return new CheckBox().anyNotNull(message, objs);
    }

    public static CheckBox assertTrue(String message, Boolean value) {
        return new CheckBox().assertTrue(message, value);
    }

    public static class CheckBox {
        public CheckBox anyNotNull(String message, Object... objs) {
            if (objs == null || objs.length == 0) {
                throw new IllegalArgumentException(message);
            }
            for (Object obj : objs) {
                if (obj != null) {
                    return this;
                }
            }
            throw new IllegalArgumentException(message);
        }
        public CheckBox anyNotEmpty(String message, CharSequence... charSequences) {
            if (charSequences == null || charSequences.length == 0) {
                throw new IllegalArgumentException(message);
            }
            for (CharSequence charSequence : charSequences) {
                if (StrUtil.isNotBlank(charSequence)) {
                    return this;
                }
            }
            throw new IllegalArgumentException(message);
        }
        public CheckBox notNull(String message, Object obj) {
            if (Objects.isNull(obj)) {
                throw new IllegalArgumentException(message);
            }
            return this;
        }

        public CheckBox notEmpty(String message, Collection<?> collection) {
            notNull(message, collection);
            if (collection.isEmpty()) {
                throw new IllegalArgumentException(message);
            }
            return this;
        }

        public CheckBox notBlank(String message, CharSequence str) {
            if (StrUtil.isBlank(str)) {
                throw new IllegalArgumentException(message);
            }
            return this;
        }

        public CheckBox assertTrue(String message, Boolean value) {
            if (value == null || !value) {
                throw new IllegalArgumentException(message);
            }
            return this;
        }
    }
}
