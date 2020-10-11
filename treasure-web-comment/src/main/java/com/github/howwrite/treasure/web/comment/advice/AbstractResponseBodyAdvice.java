package com.github.howwrite.treasure.web.comment.advice;

import com.google.common.collect.ImmutableMap;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author howwrite
 * @date 2020/10/8 10:49 上午
 */
public abstract class AbstractResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    @Nullable
    public Object beforeBodyWrite(Object body, @Nullable MethodParameter returnType, @Nullable MediaType selectedContentType, @Nullable Class<? extends HttpMessageConverter<?>> selectedConverterType, @Nullable ServerHttpRequest request, @Nullable ServerHttpResponse response) {
        return ImmutableMap.of("success", true, "result", body);
    }
}
