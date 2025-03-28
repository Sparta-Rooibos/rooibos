package com.sparta.rooibos.deliverer.infrastructure.web;

import com.sparta.rooibos.deliverer.application.auditing.UserAuditorContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuditorHeaderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String email = request.getHeader("X-User-Email"); // 원하는 키 사용
        String role = request.getHeader("X-User-Role");
        if (email != null && role != null) {
            UserAuditorContext.set(email, role);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserAuditorContext.clear();
    }
}

