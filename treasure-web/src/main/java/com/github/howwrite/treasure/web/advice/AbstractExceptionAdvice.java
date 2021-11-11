package com.github.howwrite.treasure.web.advice;

import com.github.howwrite.treasure.web.exception.WebRestException;
import com.github.howwrite.treasure.web.util.RequestUtils;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author howwrite
 * @date 2020/10/8 10:55 上午
 */
@RequiredArgsConstructor
@Slf4j
public abstract class AbstractExceptionAdvice {
    private final MessageSource messageSource;

    @ExceptionHandler(WebRestException.class)
    protected ResponseEntity<Object> onWebRestException(WebRestException e, HttpServletRequest request) {
        log.warn("request warn!" + RequestUtils.generateRequestErrorLog(request, "\n"), e);
        final String errorMessage = e.getMessage();
        final Object[] args = e.getArgs();
        String error = messageSource.getMessage(errorMessage, args, errorMessage, request.getLocale());
        HttpStatus httpStatus = HttpStatus.OK;
        if (StringUtils.isEmpty(error)) {
            log.warn("error code can not find i18n resource, errorMessage:{}", errorMessage);
            error = messageSource.getMessage("系统开小差啦", null, request.getLocale());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        final HashMap<Object, Object> values = new HashMap<>(8);
        values.put("success", false);
        values.put("error", error);
        values.put("args", args);
        return new ResponseEntity<>(values, httpStatus);
    }

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<Object> onException(Throwable e, HttpServletRequest request) {
        log.warn("request error!" + RequestUtils.generateRequestErrorLog(request, "\n"), e);
        String error = messageSource.getMessage("系统开小差啦", null, request.getLocale());
        final ImmutableMap<String, Object> values = ImmutableMap.of("success", false, "error", error);
        return new ResponseEntity<>(values, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
