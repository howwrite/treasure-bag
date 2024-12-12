package com.github.howwrite.treasure.security.config;

import com.github.howwrite.treasure.config.Config;
import com.github.howwrite.treasure.config.ConfigGenerator;
import com.github.howwrite.treasure.config.ConfigHandler;
import com.github.howwrite.treasure.config.ConfigHelper;

public class SecuritySwitch {

    @ConfigGenerator
    public static Config<SecurityKeyConfig> aesKeyConfig;

    /**
     * 允许的时间差 单位分钟
     */
    @ConfigGenerator
    public static Config<Integer> securityValidityPeriodMinute = ConfigHelper.newConfigInstance(10);

    static {
        ConfigHandler.handler(SecuritySwitch.class);
    }
}
