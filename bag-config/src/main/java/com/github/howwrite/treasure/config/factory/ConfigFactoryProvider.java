package com.github.howwrite.treasure.config.factory;

public class ConfigFactoryProvider {
    private static ConfigFactory configFactory = new DefaultConfigFactory();

    public static String readConfig(String namespace, String key) {
        return configFactory.readConfig(namespace, key);
    }

    public static void setProvider(ConfigFactory configFactory) {
        ConfigFactoryProvider.configFactory = configFactory;
    }

    public static void updateConfig(String namespace, String key, String value) {
        configFactory.updateConfig(namespace, key, value);
    }
}
