package com.github.howwrite.treasure.config;

import java.lang.reflect.Type;

public interface Config<T> {
    T calValue();

    Config<T> type(Type type);

    Config<T> namespace(String namespace);

    Config<T> key(String key);
}
