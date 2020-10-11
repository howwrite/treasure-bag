package com.github.howwrite.treasure.server.comment.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author howwrite
 * @date 2020/10/7 9:47 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ServerException extends RuntimeException {
    private static final long serialVersionUID = 1861119396443594724L;

    private String errorCode;
    private Object[] args;

    public ServerException(String error, String errorCode, Object... args) {
        super(error);
        setErrorCode(errorCode);
        setArgs(args);
    }

    public ServerException(String errorCode, Object... args) {
        setErrorCode(errorCode);
        setArgs(args);
    }

    public ServerException(String errorCode, Throwable cause) {
        super(cause);
        setErrorCode(errorCode);
    }

    public ServerException(String errorCode) {
        setErrorCode(errorCode);
    }
}
