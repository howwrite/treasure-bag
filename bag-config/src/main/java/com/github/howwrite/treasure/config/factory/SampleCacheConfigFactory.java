package com.github.howwrite.treasure.config.factory;

import com.github.howwrite.treasure.config.fetcher.ConfigFetcherProvider;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

public class SampleCacheConfigFactory implements ConfigFactory {

    LoadingCache<Pair<String, String>, String> kkvCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build(new CacheLoader<>() {
                @Nonnull
                @Override
                public String load(@Nonnull Pair<String, String> key) {
                    return ConfigFetcherProvider.provideConfigFetcher(key.getKey(), key.getValue()).readConfig(key.getKey(), key.getValue());
                }
            });

    @Override
    public String readConfig(String namespace, String key) {
        return kkvCache.getUnchecked(Pair.of(namespace, key));
    }

    @Override
    public void updateConfig(String namespace, String key, String value) {
        kkvCache.refresh(Pair.of(namespace, key));
    }
}
