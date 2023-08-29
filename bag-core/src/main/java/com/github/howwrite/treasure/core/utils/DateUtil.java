package com.github.howwrite.treasure.core.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DateUtil {

    private static final Map<String, DateTimeFormatter> FORMATTER_MAP = new ConcurrentHashMap<>();


    public static LocalDateTime strToLocalDateTime(String dateString, String formatStr) {
        DateTimeFormatter dateTimeFormatter = FORMATTER_MAP.get(formatStr);
        if (dateTimeFormatter == null) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(formatStr);
            FORMATTER_MAP.put(formatStr, dateTimeFormatter);
        }
        return LocalDateTime.parse(dateString, dateTimeFormatter);
    }

}