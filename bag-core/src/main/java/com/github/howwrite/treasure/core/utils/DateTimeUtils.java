package com.github.howwrite.treasure.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 日期时间工具类，提供线程安全的格式化与解析能力。
 */
@Slf4j
public final class DateTimeUtils {

    private static final Map<String, DateTimeFormatter> FORMATTER_CACHE = new ConcurrentHashMap<>();

    private DateTimeUtils() {
    }

    /**
     * 安全解析字符串为 {@link LocalDateTime}，失败时返回 {@code null}。
     */
    public static LocalDateTime safeStr2LocalDateTime(String dateTimeStr, String formatStr) {
        return parseLocalDateTime(dateTimeStr, formatStr, null);
    }

    /**
     * 解析字符串为 {@link LocalDateTime}，失败返回默认值。
     */
    public static LocalDateTime parseLocalDateTime(String dateTimeStr, String formatStr, LocalDateTime defaultValue) {
        if (StringUtils.isBlank(dateTimeStr) || StringUtils.isBlank(formatStr)) {
            log.warn("解析 LocalDateTime 失败，输入为空，dateTimeStr:{}，format:{}", dateTimeStr, formatStr);
            return defaultValue;
        }
        DateTimeFormatter formatter = resolveFormatter(formatStr);
        if (formatter == null) {
            return defaultValue;
        }
        try {
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (Exception e) {
            log.error("解析 LocalDateTime 异常，dateTimeStr:{}，format:{}", dateTimeStr, formatStr, e);
            return defaultValue;
        }
    }

    /**
     * 安全格式化 {@link LocalDateTime}，失败时返回 {@code null}。
     */
    public static String safeLocalDateTime2Str(LocalDateTime localDateTime, String formatStr) {
        return formatLocalDateTime(localDateTime, formatStr, null);
    }

    /**
     * 格式化 {@link LocalDateTime}，失败返回默认值。
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime, String formatStr, String defaultValue) {
        if (localDateTime == null || StringUtils.isBlank(formatStr)) {
            log.warn("格式化 LocalDateTime 失败，输入为空，localDateTime:{}，format:{}", localDateTime, formatStr);
            return defaultValue;
        }
        DateTimeFormatter formatter = resolveFormatter(formatStr);
        if (formatter == null) {
            return defaultValue;
        }
        try {
            return formatter.format(localDateTime);
        } catch (Exception e) {
            log.error("格式化 LocalDateTime 异常，localDateTime:{}，format:{}", localDateTime, formatStr, e);
            return defaultValue;
        }
    }

    /**
     * 安全解析字符串为 {@link LocalDate}，失败时返回 {@code null}。
     */
    public static LocalDate safeStr2LocalDate(String dateStr, String formatStr) {
        return parseLocalDate(dateStr, formatStr, null);
    }

    /**
     * 解析字符串为 {@link LocalDate}，失败返回默认值。
     */
    public static LocalDate parseLocalDate(String dateStr, String formatStr, LocalDate defaultValue) {
        if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(formatStr)) {
            log.warn("解析 LocalDate 失败，输入为空，dateStr:{}，format:{}", dateStr, formatStr);
            return defaultValue;
        }
        DateTimeFormatter formatter = resolveFormatter(formatStr);
        if (formatter == null) {
            return defaultValue;
        }
        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            log.error("解析 LocalDate 异常，dateStr:{}，format:{}", dateStr, formatStr, e);
            return defaultValue;
        }
    }

    /**
     * 安全格式化 {@link LocalDate}，失败时返回 {@code null}。
     */
    public static String safeLocalDate2Str(LocalDate localDate, String formatStr) {
        return formatLocalDate(localDate, formatStr, null);
    }

    /**
     * 格式化 {@link LocalDate}，失败返回默认值。
     */
    public static String formatLocalDate(LocalDate localDate, String formatStr, String defaultValue) {
        if (localDate == null || StringUtils.isBlank(formatStr)) {
            log.warn("格式化 LocalDate 失败，输入为空，localDate:{}，format:{}", localDate, formatStr);
            return defaultValue;
        }
        DateTimeFormatter formatter = resolveFormatter(formatStr);
        if (formatter == null) {
            return defaultValue;
        }
        try {
            return formatter.format(localDate);
        } catch (Exception e) {
            log.error("格式化 LocalDate 异常，localDate:{}，format:{}", localDate, formatStr, e);
            return defaultValue;
        }
    }

    /**
     * 格式化当前时间，常用于日志或业务展示。
     */
    public static String formatNow(String formatStr) {
        return formatLocalDateTime(LocalDateTime.now(), formatStr, null);
    }

    /**
     * 从缓存中获取对应格式的 {@link DateTimeFormatter}，非法格式会记录日志并返回 {@code null}。
     */
    private static DateTimeFormatter resolveFormatter(String formatStr) {
        try {
            return FORMATTER_CACHE.computeIfAbsent(formatStr, DateTimeFormatter::ofPattern);
        } catch (IllegalArgumentException e) {
            FORMATTER_CACHE.remove(formatStr);
            log.error("创建 DateTimeFormatter 失败，format:{}", formatStr, e);
            return null;
        }
    }
}
