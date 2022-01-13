package com.github.howwrite.treasure.web.util;

import com.github.howwrite.treasure.api.response.Response;
import com.github.howwrite.treasure.web.exception.WebRestException;

/**
 * @author howwrite
 * @date 2021/10/4 11:15 下午
 */
public class WebResultUtil {
    public static <T> T resultOrThrow(Response<T> result) {
        if (result.isOk()) {
            return result.getData();
        }
        throw new WebRestException( result.getError(), result.getArgs());
    }
}
