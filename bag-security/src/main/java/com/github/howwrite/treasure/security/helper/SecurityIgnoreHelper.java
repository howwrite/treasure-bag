package com.github.howwrite.treasure.security.helper;

import com.github.howwrite.treasure.security.annotation.SecurityIgnore;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

/**
 * 安全忽略注解辅助工具。
 */
public final class SecurityIgnoreHelper {

    private SecurityIgnoreHelper() {
    }

    public static boolean shouldIgnore(HttpInputMessage inputMessage) {
        if (!(inputMessage instanceof ServletServerHttpRequest servletRequest)) {
            return false;
        }
        Object handler = servletRequest.getServletRequest().getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return false;
        }
        return shouldIgnore(handlerMethod);
    }

    public static boolean shouldIgnore(HandlerMethod handlerMethod) {
        return AnnotatedElementUtils.hasAnnotation(handlerMethod.getMethod(), SecurityIgnore.class)
                || AnnotatedElementUtils.hasAnnotation(handlerMethod.getBeanType(), SecurityIgnore.class);
    }
}
