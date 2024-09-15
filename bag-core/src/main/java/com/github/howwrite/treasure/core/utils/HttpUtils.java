package com.github.howwrite.treasure.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.collections4.MapUtils;

import javax.annotation.Nullable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class HttpUtils {
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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
}
