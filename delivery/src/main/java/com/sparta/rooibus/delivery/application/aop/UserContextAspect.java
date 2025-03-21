package com.sparta.rooibus.delivery.application.aop;

import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class UserContextAspect {

    private final UserContextRequestBean userContext;

    @Before("execution(* com.sparta.rooibus.delivery.presentation.controller..*(..))")
    public void extractUserContext(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            System.out.println("RequestContextHolder에서 request를 가져올 수 없음");
            return;
        }

        HttpServletRequest request = attributes.getRequest();
        String role = request.getHeader("role");
        String userIdStr = request.getHeader("userId");

        if (role != null && userIdStr != null) {
            try {
                UUID userId = UUID.fromString(userIdStr);
                userContext.set(role,userId);
                System.out.println("UserContext에 값 저장됨: Role=" + role + ", UserId=" + userId);
            } catch (IllegalArgumentException e) {
                System.out.println("UserId 변환 실패: " + userIdStr);
            }
        } else {
            System.out.println("헤더에 role 또는 userId 없음");
        }
    }
}
