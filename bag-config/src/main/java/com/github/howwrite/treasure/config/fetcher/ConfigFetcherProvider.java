package com.github.howwrite.treasure.config.fetcher;

import java.util.function.BiFunction;

public class ConfigFetcherProvider {

    private static BiFunction<String, String, ConfigFetcher> provider = (namespace, key) -> new FileFetcher();

    public static ConfigFetcher provideConfigFetcher(String namespace, String key) {
        return provider.apply(namespace, key);
    }

    public static void setProvider(BiFunction<String, String, ConfigFetcher> provider) {
        ConfigFetcherProvider.provider = provider;
    }
}
