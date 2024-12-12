package com.github.howwrite.treasure.security.config;

import lombok.Data;

import java.util.Map;

@Data
public class SecurityKeyConfig {
    /**
     * 当前配置
     */
    private Map<String, String> now;
    /**
     * 过时配置
     */
    private Map<String, String> deprecated;
}
