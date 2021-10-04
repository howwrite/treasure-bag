package com.github.howwrite.treasure.server.exception;

/**
 * 服务异常，不会透出具体的异常信息
 *
 * @author howwrite
 * @date 2021/10/4 10:32 下午
 */
public class ServerException extends RuntimeException {
    public ServerException() {
        super();
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerException(Throwable cause) {
        super(cause);
    }

    protected ServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
