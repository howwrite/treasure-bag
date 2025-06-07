package com.github.howwrite.treasure.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.Request.Builder;
import org.apache.commons.collections4.MapUtils;

import javax.annotation.Nullable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class HttpUtils {
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(6, TimeUnit.HOURS) // 连接超时时间
            .readTimeout(6, TimeUnit.HOURS)    // 读取超时时间
            .writeTimeout(6, TimeUnit.HOURS)   // 写入超时时间
            .build();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * 使用OkHttp发送GET请求并获取地址信息
     *
     * @param url    请求的URL地址
     * @param params 请求的参数
     * @return 返回从服务器获取的地址信息
     */
    @Nullable
    public static <T> T get(String url, Map<String, String> params, Function<String, T> resultHandler) {
        try {
            // 构建参数字符串
            if (MapUtils.isNotEmpty(params)) {
                url += '?' + params.entrySet().stream().map(it -> URLEncoder.encode(it.getKey(), StandardCharsets.UTF_8) + "=" + URLEncoder.encode(it.getValue(), StandardCharsets.UTF_8))
                        .collect(Collectors.joining("&"));
            }

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    return resultHandler.apply(response.body().string());
                }
            }
        } catch (Exception e) {
            log.warn("get url error, url:{}", url, e);
        }
        return null;
    }

    @Nullable
    public static <T> T get(String url, Map<String, String> params, Class<T> clazz) {
        return get(url, params, t -> {
            try {
                return OBJECT_MAPPER.readValue(t, clazz);
            } catch (JsonProcessingException e) {
                log.warn("parse get result error, url:{}", url, e);
                return null;
            }
        });
    }

    /**
     * 使用OkHttp发送POST请求
     */
    @Nullable
    public static <T> T post(String url, Map<String, String> headers, String jsonBody, Function<String, T> resultHandler) {
        try {
            Builder requestBuilder = new Builder()
                    .url(url)
                    .post(RequestBody.create(jsonBody, JSON));
            if (MapUtils.isNotEmpty(headers)) {
                headers.forEach(requestBuilder::addHeader);
            }

            try (Response response = client.newCall(requestBuilder.build()).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    return resultHandler.apply(response.body().string());
                }
            }
        } catch (Exception e) {
            log.warn("post url error, url:{}", url, e);
        }
        return null;
    }
    @Nullable
    public static <T> T post(String url, Map<String, String> headers, String jsonBody, Class<T> clazz) {
        return post(url, headers, jsonBody, t -> {
            try {
                return OBJECT_MAPPER.readValue(t, clazz);
            } catch (JsonProcessingException e) {
                log.warn("parse post result error, url:{}", url, e);
                return null;
            }
        });
    }

    /**
     * 获取url中的文件扩展名
     * http://example.com/path/to/file.jpg?param=value -> jpg
     */
    public static String calFileExtension(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }

        // 处理#后面的内容
        int hashIndex = url.indexOf('#');
        if (hashIndex != -1) {
            url = url.substring(0, hashIndex);
        }

        // 处理?后面的内容
        int queryIndex = url.indexOf('?');
        if (queryIndex != -1) {
            url = url.substring(0, queryIndex);
        }

        // 获取最后一个路径段
        int lastSlashIndex = url.lastIndexOf('/');
        String fileName = lastSlashIndex != -1 ? url.substring(lastSlashIndex + 1) : url;

        // 查找最后一个点的位置
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == 0) {
            return null;
        }

        return fileName.substring(dotIndex + 1);
    }
}
