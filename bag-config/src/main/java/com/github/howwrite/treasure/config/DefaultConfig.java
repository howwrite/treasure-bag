package com.github.howwrite.treasure.config;

import com.alibaba.fastjson2.JSONObject;
import com.github.howwrite.treasure.config.factory.ConfigFactoryProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
public class DefaultConfig<T> implements Config<T> {
    @Nullable
    protected final Supplier<T> defaultSupplier;
    protected String namespace;
    protected String key;
    protected Type type;

    public DefaultConfig(@Nullable Supplier<T> defaultSupplier) {
        this.defaultSupplier = defaultSupplier;
    }

    @Override
    public T calValue() {
        try {
            String configStr = ConfigFactoryProvider.readConfig(namespace, key);
            return Optional.ofNullable(convertValue(configStr, type)).orElseGet(this::defaultValue);
        } catch (Exception e) {
            log.error("call value failed, namespace:{}, key:{}", namespace, key, e);
            return defaultValue();
        }
    }

    protected T defaultValue() {
        return Optional.ofNullable(defaultSupplier).map(Supplier::get).orElse(null);
    }


    @Override
    public Config<T> type(Type type) {
        this.type = type;
        return this;
    }

    @Override
    public Config<T> key(String key) {
        this.key = key;
        return this;
    }

    @Override
    public Config<T> namespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    protected T convertValue(String content, Type type) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        if (String.class.equals(type)) {
            return (T) content;
        }
        if (Integer.class.equals(type) || int.class.equals(type)) {
            return (T) (Integer) Integer.parseInt(content);
        }
        if (Long.class.equals(type) || long.class.equals(type)) {
            return (T) (Long) Long.parseLong(content);
        }
        if (Double.class.equals(type) || double.class.equals(type)) {
            return (T) (Double) Double.parseDouble(content);
        }
        if (Float.class.equals(type) || float.class.equals(type)) {
            return (T) (Float) Float.parseFloat(content);
        }
        if (Boolean.class.equals(type) || boolean.class.equals(type)) {
            return (T) (Boolean) Boolean.parseBoolean(content);
        }
        if (Byte.class.equals(type) || byte.class.equals(type)) {
            return (T) (Byte) Byte.parseByte(content);
        }
        if (Character.class.equals(type) || char.class.equals(type)) {
            return (T) (Character) content.charAt(0);
        }
        if (Short.class.equals(type) || short.class.equals(type)) {
            return (T) (Short) Short.parseShort(content);
        }
        if (BigDecimal.class.equals(type)) {
            return (T) new BigDecimal(content);
        }
        return JSONObject.parseObject(content, type);
    }
}
