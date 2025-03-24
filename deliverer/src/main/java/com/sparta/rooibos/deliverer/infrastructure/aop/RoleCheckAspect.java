package com.sparta.rooibos.deliverer.infrastructure.aop;

import com.sparta.rooibos.deliverer.infrastructure.auditing.UserAuditorContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class RoleCheckAspect {

    private final HttpServletRequest request;

    @Around("@annotation(roleCheck)")
    public Object checkRole(ProceedingJoinPoint joinPoint, RoleCheck roleCheck) throws Throwable {
        String role = UserAuditorContext.getRole();
        if (role == null || role.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "권한 정보가 없습니다.");
        }

        List<String> allowedRoles = Arrays.asList(roleCheck.value());

        if (allowedRoles.contains("ALL") || allowedRoles.contains(role.toUpperCase())) {
            return joinPoint.proceed();
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
    }
}

