package com.github.howwrite.treasure.config;

public interface Config {
    <T> T calValue(String key, Class<T> clazz, T defaultValue);
}
