package com.github.howwrite.treasure.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public abstract class AbstractConfig implements Config {
    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    protected abstract <T> T calValue(String key, Class<T> clazz);

    @Override
    public <T> T calValue(String key, Class<T> clazz, T defaultValue) {
        try {
            return Optional.ofNullable(calValue(key, clazz))
                    .orElse(defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }


    protected <T> T convertValue(String content, Class<T> clazz) {
        if (String.class.equals(clazz)) {
            return (T) content;
        }
        if (Integer.class.equals(clazz) || int.class.equals(clazz)) {
            return (T) (Integer) Integer.parseInt(content);
        }
        if (Long.class.equals(clazz) || long.class.equals(clazz)) {
            return (T) (Long) Long.parseLong(content);
        }
        if (Double.class.equals(clazz) || double.class.equals(clazz)) {
            return (T) (Double) Double.parseDouble(content);
        }
        if (Float.class.equals(clazz) || float.class.equals(clazz)) {
            return (T) (Float) Float.parseFloat(content);
        }
        if (Boolean.class.equals(clazz) || boolean.class.equals(clazz)) {
            return (T) (Boolean) Boolean.parseBoolean(content);
        }
        if (Byte.class.equals(clazz) || byte.class.equals(clazz)) {
            return (T) (Byte) Byte.parseByte(content);
        }
        if (Character.class.equals(clazz) || char.class.equals(clazz)) {
            return (T) (Character) content.charAt(0);
        }
        if (Short.class.equals(clazz) || short.class.equals(clazz)) {
            return (T) (Short) Short.parseShort(content);
        }
        try {
            return OBJECT_MAPPER.readValue(content, clazz);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
