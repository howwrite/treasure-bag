package com.github.howwrite.treasure.core;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.function.Function;

/**
 * @author howwrite
 * @date 2020/10/7 12:22 上午
 */
@Data
public final class Response<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 7937244760344738860L;
    private T data;
    private boolean ok;
    private String error;
    private Object[] args;

    public static <T> Response<T> ok(T data) {
        final Response<T> response = new Response<>();
        response.setData(data);
        response.setOk(true);
        return response;
    }
    public static <T> Response<T> fail(String error, Object... args) {
        final Response<T> response = new Response<>();
        response.setOk(false);
        response.setError(error);
        response.setArgs(args);
        return response;
    }

    public static <T, R> Response<R> convert(Response<T> resp, Function<T, R> apply) {
        if (apply == null) {
            throw new NullPointerException("convert function is null");
        }
        if (!resp.isOk()) {
            return Response.fail(resp.getError(), resp.getArgs());
        }
        return Response.ok(apply.apply(resp.getData()));
    }
}
