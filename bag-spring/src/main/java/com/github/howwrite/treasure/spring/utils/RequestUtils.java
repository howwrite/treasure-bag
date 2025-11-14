package com.github.howwrite.treasure.spring.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Objects;

public class RequestUtils {

    public static void writeCookie(HttpServletResponse response, String cookieName, String cookieValue, Integer liveToSeconds) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(Objects.requireNonNullElse(liveToSeconds, -1));
        response.addCookie(cookie);
    }
}