package com.github.howwrite.treasure.core;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class BizLog {
    private static final ThreadLocal<Map<String, Object>> logMaps = new ThreadLocal<>();

    public static void start() {
        logMaps.set(new LinkedHashMap<>());
    }

    public static void end() {
        Map<String, Object> logMap = logMaps.get();
        if (logMap == null) return;
        String logText = logMap.entrySet().stream().map(it -> it.getKey() + "=" + it.getValue()).collect(Collectors.joining("|"));
        log.info(logText);
        logMaps.remove();
    }

    public void log(String key, Object value) {
        if (logMaps.get() == null) {
            start();
        }
        logMaps.get().put(key, value);
    }
}
