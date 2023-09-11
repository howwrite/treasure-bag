package com.github.howwrite.treasure.core.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DateUtils {

    private static final Map<String, DateTimeFormatter> FORMATTER_MAP = new ConcurrentHashMap<>();


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