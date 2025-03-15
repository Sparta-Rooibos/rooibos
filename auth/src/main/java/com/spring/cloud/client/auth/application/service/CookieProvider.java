package com.spring.cloud.client.auth.application.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CookieProvider {
    Cookie createCookie(String value);
    String getRefreshToken(HttpServletRequest request);
    void deleteRefreshToken(HttpServletResponse response);
}
