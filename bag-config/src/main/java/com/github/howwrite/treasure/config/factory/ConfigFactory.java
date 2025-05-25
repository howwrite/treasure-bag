package com.github.howwrite.treasure.config.factory;

public interface ConfigFactory {
    String readConfig(String namespace, String key);

    void updateConfig(String namespace, String key, String value);
}
