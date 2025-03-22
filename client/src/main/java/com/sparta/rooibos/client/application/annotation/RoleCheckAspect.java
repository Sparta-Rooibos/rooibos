package com.sparta.rooibos.client.application.annotation;

import com.sparta.rooibos.client.application.type.Role;
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
        String role = request.getHeader("X-User-Role");
        List<String> roleNameList = Arrays.stream(roleCheck.value()).map(Role::getAuthority).toList();


        // 역할이 존재하지 않는 경우에는 넘어간다.
        if (roleNameList.isEmpty()) {
            return joinPoint.proceed();
        }

        // 접근권한이 존재하지 않는경우
        if (!roleNameList.contains(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }

        return joinPoint.proceed();
    }
}