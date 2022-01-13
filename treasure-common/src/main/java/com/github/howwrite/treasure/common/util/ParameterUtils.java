package com.github.howwrite.treasure.common.util;

import cn.hutool.core.util.StrUtil;

import java.util.Collection;
import java.util.Objects;

/**
 * @author howwrite
 * @date 2020/10/7 9:44 下午
 */
public class ParameterUtils {
    public static ParameterCheckFactor notNull(String message, Object obj) {
        return new ParameterCheckFactor().notNull(message, obj);
    }

    public static ParameterCheckFactor notEmpty(String message, Collection<?> collection) {
        return new ParameterCheckFactor().notEmpty(message, collection);
    }

    public static ParameterCheckFactor notBlank(String message, CharSequence str) {
        return new ParameterCheckFactor().anyNotNull(message, str);
    }

    public static ParameterCheckFactor anyNotEmpty(String message, CharSequence... charSequences) {
        return new ParameterCheckFactor().anyNotEmpty(message, charSequences);
    }

    public static ParameterCheckFactor anyNotNull(String message, Object... objs) {
        return new ParameterCheckFactor().anyNotNull(message, objs);
    }
    private static class ParameterCheckFactor{
        public ParameterCheckFactor anyNotNull(String message, Object... objs) {
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
        public ParameterCheckFactor anyNotEmpty(String message, CharSequence... charSequences) {
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
        public ParameterCheckFactor notNull(String message, Object obj) {
            if (Objects.isNull(obj)) {
                throw new IllegalArgumentException(message);
            }
            return this;
        }

        public ParameterCheckFactor notEmpty(String message, Collection<?> collection) {
            notNull(message, collection);
            if (collection.isEmpty()) {
                throw new IllegalArgumentException(message);
            }
            return this;
        }

        public ParameterCheckFactor notBlank(String message, CharSequence str) {
            if (StrUtil.isBlank(str)) {
                throw new IllegalArgumentException(message);
            }
            return this;
        }
    }
}
