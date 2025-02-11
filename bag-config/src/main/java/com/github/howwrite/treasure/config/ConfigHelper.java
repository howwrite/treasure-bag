package com.github.howwrite.treasure.config;

import java.util.function.Supplier;

public class ConfigHelper {
    public static <T> Config<T> newConfigInstance() {
        return newConfigInstance(null);
    }

    public static <T> Config<T> newConfigInstance(Supplier<T> defaultSupplier) {
        return new DefaultConfig<>(defaultSupplier);
    }

    public static <T> Config<T> newConfigInstance(T defaultValue) {
        return newConfigInstance(() -> defaultValue);
    }
}
