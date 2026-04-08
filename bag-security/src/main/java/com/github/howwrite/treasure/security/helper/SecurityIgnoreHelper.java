package com.github.howwrite.treasure.security.helper;

import com.github.howwrite.treasure.security.annotation.SecurityIgnore;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

/**
 * 安全忽略注解辅助工具。
 */
public final class SecurityIgnoreHelper {

    private SecurityIgnoreHelper() {
    }

    public static boolean shouldIgnore(HttpInputMessage inputMessage) {
        HandlerMethod currentHandlerMethod = resolveCurrentHandlerMethod();
        if (currentHandlerMethod != null) {
            return shouldIgnore(currentHandlerMethod);
        }
        if (!(inputMessage instanceof ServletServerHttpRequest servletRequest)) {
            return false;
        }
        HandlerMethod handlerMethod = extractHandlerMethod(servletRequest.getServletRequest());
        return handlerMethod != null && shouldIgnore(handlerMethod);
    }

    private static HandlerMethod resolveCurrentHandlerMethod() {
        if (!(RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes servletRequestAttributes)) {
            return null;
        }
        return extractHandlerMethod(servletRequestAttributes.getRequest());
    }

    private static HandlerMethod extractHandlerMethod(HttpServletRequest request) {
        Object handler = request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return null;
        }
        return handlerMethod;
    }

    public static boolean shouldIgnore(HandlerMethod handlerMethod) {
        return AnnotatedElementUtils.hasAnnotation(handlerMethod.getMethod(), SecurityIgnore.class)
                || AnnotatedElementUtils.hasAnnotation(handlerMethod.getBeanType(), SecurityIgnore.class);
    }
}
