package com.github.howwrite.treasure.web.exception;

import com.github.howwrite.treasure.api.response.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author howwrite
 * @date 2020/10/7 12:38 上午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WebRestException extends RuntimeException {
    private static final long serialVersionUID = 8919621441431800289L;

    private String errorCode;
    private Object[] args;

    public WebRestException(String error, String errorCode, Object... args) {
        super(error);
        setErrorCode(errorCode);
        setArgs(args);
    }

    public WebRestException(String errorCode, Object... args) {
        setErrorCode(errorCode);
        setArgs(args);
    }

    public WebRestException(String errorCode, Throwable cause) {
        super(cause);
        setErrorCode(errorCode);
    }

    public WebRestException(Throwable cause) {
        super(cause);
    }

    public WebRestException(String errorCode) {
        setErrorCode(errorCode);
    }

    public WebRestException(Response<?> failResponse) {
        super(failResponse.getError());
        setErrorCode(failResponse.getErrorCode());
        setArgs(failResponse.getArgs());
    }
}
