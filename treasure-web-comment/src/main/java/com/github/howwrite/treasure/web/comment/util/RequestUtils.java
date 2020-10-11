package com.github.howwrite.treasure.web.comment.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * @author howwrite
 * @date 2020/10/8 11:04 上午
 */
public class RequestUtils {
    /**
     * 生成一个request对象的日志
     *
     * @param request request对象
     * @return 描述该对象的日志
     */
    public static String generateRequestErrorLog(HttpServletRequest request, String joiner) {
        StringBuilder sb = new StringBuilder("path: ").append(request.getMethod()).append(request.getServletPath()).append(joiner);
        sb.append("params: ").append(toJsonString(request.getParameterMap())).append(joiner);
        sb.append("cookie: ").append(toJsonString(request.getCookies()));
        return sb.toString();
    }

    public static String toJsonString(Object o) {
        return JSONObject.toJSONString(o);
    }
}
