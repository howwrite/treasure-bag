package com.github.howwrite.treasure.web.util;

import com.github.howwrite.treasure.api.response.Response;
import com.github.howwrite.treasure.web.exception.WebRestException;

/**
 * @author howwrite
 * @date 2021/10/4 11:15 下午
 */
public class WebResultUtil {
    public static <T> T resultOrThrow(Response<T> result) {
        if (result.isSuccess()) {
            return result.getData();
        }
        throw new WebRestException(result.getErrorCode(), result.getError(), result.getArgs());
    }
}
