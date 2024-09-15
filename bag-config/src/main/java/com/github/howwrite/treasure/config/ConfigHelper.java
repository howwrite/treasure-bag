package com.github.howwrite.treasure.config;

import com.github.howwrite.treasure.config.reader.FileConfig;

import java.util.function.Supplier;

public class ConfigHelper {
    public static <T> Config<T> newConfigInstance() {
        return new FileConfig<>();
    }

    public static <T> Config<T> newConfigInstance(Supplier<T> defaultSupplier) {
        return new FileConfig<T>().defaultValue(defaultSupplier);
    }

    public static <T> Config<T> newConfigInstance(T defaultValue) {
        return newConfigInstance(() -> defaultValue);
    }
}
