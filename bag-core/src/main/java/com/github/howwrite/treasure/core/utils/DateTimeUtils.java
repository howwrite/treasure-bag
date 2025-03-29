package com.github.howwrite.treasure.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DateTimeUtils {

    private static final Map<String, DateTimeFormatter> FORMATTER_MAP = new ConcurrentHashMap<>();


    public static LocalDateTime safeStr2LocalDateTime(String dateTimeStr, String formatStr) {
        try {
            if (StringUtils.isBlank(dateTimeStr) || StringUtils.isBlank(formatStr)) {
                log.warn("dateTimeStr or formatStr is empty, dateTimeStr:{}, formatStr:{}", dateTimeStr, formatStr);
                return null;
            }
            DateTimeFormatter dateTimeFormatter = FORMATTER_MAP.get(formatStr);
            if (dateTimeFormatter == null) {
                dateTimeFormatter = DateTimeFormatter.ofPattern(formatStr);
                FORMATTER_MAP.put(formatStr, dateTimeFormatter);
            }
            return LocalDateTime.parse(dateTimeStr, dateTimeFormatter);
        } catch (Exception e) {
            log.error("str 2 localDateTime error, dateTimeStr:{}, formatStr:{}", dateTimeStr, formatStr, e);
            return null;
        }
    }

    public static String safeLocalDateTime2Str(LocalDateTime localDateTime, String formatStr) {
        try {
            if (localDateTime == null || StringUtils.isBlank(formatStr)) {
                log.warn("localDateTime or formatStr is empty, localDateTime:{}, formatStr:{}", localDateTime, formatStr);
                return null;
            }
            DateTimeFormatter dateTimeFormatter = FORMATTER_MAP.get(formatStr);
            if (dateTimeFormatter == null) {
                dateTimeFormatter = DateTimeFormatter.ofPattern(formatStr);
                FORMATTER_MAP.put(formatStr, dateTimeFormatter);
            }
            return localDateTime.format(dateTimeFormatter);
        } catch (Exception e) {
            log.error("safeLocalDateTime2Str error, localDateTime:{}, formatStr:{}", localDateTime, formatStr, e);
            return null;
        }
    }

    public static LocalDate safeStr2LocalDate(String dateStr, String formatStr) {
        try {
            if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(formatStr)) {
                log.warn("dateStr or formatStr is empty, dateStr:{}, formatStr:{}", dateStr, formatStr);
                return null;
            }
            DateTimeFormatter dateTimeFormatter = FORMATTER_MAP.get(formatStr);
            if (dateTimeFormatter == null) {
                dateTimeFormatter = DateTimeFormatter.ofPattern(formatStr);
                FORMATTER_MAP.put(formatStr, dateTimeFormatter);
            }
            return LocalDate.parse(dateStr, dateTimeFormatter);
        } catch (Exception e) {
            log.error("safeStr2LocalDate error, dateStr:{}, formatStr:{}", dateStr, formatStr, e);
            return null;
        }
    }

    public static String safeLocalDate2Str(LocalDate localDate, String formatStr) {
        try {
            if (localDate == null || StringUtils.isBlank(formatStr)) {
                log.warn("localDate or formatStr is empty, localDate:{}, formatStr:{}", localDate, formatStr);
                return null;
            }
            DateTimeFormatter dateTimeFormatter = FORMATTER_MAP.get(formatStr);
            if (dateTimeFormatter == null) {
                dateTimeFormatter = DateTimeFormatter.ofPattern(formatStr);
                FORMATTER_MAP.put(formatStr, dateTimeFormatter);
            }
            return localDate.format(dateTimeFormatter);
        } catch (Exception e) {
            log.error("v error, localDate:{}, formatStr:{}", localDate, formatStr, e);
            return null;
        }
    }

}