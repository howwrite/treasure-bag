package com.github.howwrite.treasure.config;

import java.lang.reflect.Type;
import java.util.function.Supplier;

public interface Config<T> {
    T calValue();

    Config<T> type(Type type);

    Config<T> prefix(String prefix);

    Config<T> key(String key);

    Config<T> defaultValue(Supplier<T> defaultSupplier);
}
