package com.sparta.rooibus.order.application.aop;

import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
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

    @Before("execution(* com.sparta.rooibus.order.presentation.controller..*(..))")
    public void extractUserContext(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();
        String role = request.getHeader("X-User-Role");
        String name  = request.getHeader("X-User-Name");
        String email = request.getHeader("X-User-Email");


        if (role != null && name != null && email != null) {
            try {
                UUID userId = UUID.fromString(request.getHeader("X-User-Id"));
                userContext.set(role,email,name,userId);
                System.out.println("UserContext에 값 저장됨: Role=" + role + ", email=" + email+", name=" + name);
            } catch (Exception e) {
                System.out.println("UserContext에 값 저장하는 과정중에 에러 발생");
            }
        } else {
            System.out.println("헤더에 로그인한 정보가 없음");
        }
    }
}
