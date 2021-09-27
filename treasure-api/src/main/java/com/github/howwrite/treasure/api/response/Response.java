package com.github.howwrite.treasure.api.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author howwrite
 * @date 2020/10/7 12:22 上午
 */
@Data
public final class Response<T> implements Serializable {
    private static final long serialVersionUID = 7937244760344738860L;
    private T data;
    private boolean success;
    private String errorCode;
    private String error;
    private Object[] args;

    public static <T> Response<T> ok(T data) {
        final Response<T> response = new Response<>();
        response.setData(data);
        response.setSuccess(true);
        return response;
    }

    public static <T> Response<T> fail(String error, String errorCode, Object... args) {
        final Response<T> response = fail(error, errorCode);
        response.setArgs(args);
        return response;
    }

    public static <T> Response<T> fail(String error, String errorCode) {
        final Response<T> response = fail(error);
        response.setErrorCode(errorCode);
        return response;
    }

    public static <T> Response<T> fail(String error) {
        final Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setError(error);
        return response;
    }
}
