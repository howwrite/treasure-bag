package com.github.howwrite.treasure.config.fetcher;

public interface ConfigFetcher {
    String readConfig(String namespace, String key);
}
