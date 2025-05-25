package com.github.howwrite.treasure.config.factory;

import com.github.howwrite.treasure.config.fetcher.ConfigFetcherProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultConfigFactory implements ConfigFactory {

    @Override
    public String readConfig(String namespace, String key) {
        return ConfigFetcherProvider.provideConfigFetcher(namespace, key).readConfig(namespace, key);
    }

    @Override
    public void updateConfig(String namespace, String key, String value) {

    }
}
