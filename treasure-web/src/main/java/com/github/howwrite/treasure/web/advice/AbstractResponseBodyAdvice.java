package com.github.howwrite.treasure.web.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author howwrite
 * @date 2020/10/8 10:49 上午
 */
public abstract class AbstractResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    @Nullable
    public Object beforeBodyWrite(Object body, @Nullable MethodParameter returnType, @Nullable MediaType selectedContentType, @Nullable Class<? extends HttpMessageConverter<?>> selectedConverterType, @Nullable ServerHttpRequest request, @Nullable ServerHttpResponse response) {
        if (body instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) body;
            Object success = map.get("ok");
            if (success instanceof Boolean && Boolean.FALSE.equals(success)) {
                return body;
            }

        }
        Map<String, Object> result = new HashMap<>();
        result.put("ok", true);
        result.put("data", body);
        return result;
    }
}
