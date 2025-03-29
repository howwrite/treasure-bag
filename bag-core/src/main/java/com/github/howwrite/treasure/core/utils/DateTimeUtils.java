package com.github.howwrite.treasure.core.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DateTimeUtils {

    private static final Map<String, DateTimeFormatter> FORMATTER_MAP = new ConcurrentHashMap<>();


    public static LocalDateTime str2LocalDateTime(String dateTimeStr, String formatStr) {
        DateTimeFormatter dateTimeFormatter = FORMATTER_MAP.get(formatStr);
        if (dateTimeFormatter == null) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(formatStr);
            FORMATTER_MAP.put(formatStr, dateTimeFormatter);
        }
        return LocalDateTime.parse(dateTimeStr, dateTimeFormatter);
    }

    public static String localDateTime2Str(LocalDateTime localDate, String formatStr) {
        DateTimeFormatter dateTimeFormatter = FORMATTER_MAP.get(formatStr);
        if (dateTimeFormatter == null) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(formatStr);
            FORMATTER_MAP.put(formatStr, dateTimeFormatter);
        }
        return localDate.format(dateTimeFormatter);
    }

    public static LocalDate str2LocalDate(String dateStr, String formatStr) {
        DateTimeFormatter dateTimeFormatter = FORMATTER_MAP.get(formatStr);
        if (dateTimeFormatter == null) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(formatStr);
            FORMATTER_MAP.put(formatStr, dateTimeFormatter);
        }
        return LocalDate.parse(dateStr, dateTimeFormatter);
    }

    public static String localDate2Str(LocalDate localDate, String formatStr) {
        DateTimeFormatter dateTimeFormatter = FORMATTER_MAP.get(formatStr);
        if (dateTimeFormatter == null) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(formatStr);
            FORMATTER_MAP.put(formatStr, dateTimeFormatter);
        }
        return localDate.format(dateTimeFormatter);
    }

}