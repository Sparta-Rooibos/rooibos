package com.sparta.rooibos.common.aop;


import com.sparta.rooibos.common.utils.JwtRoleParser;
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

    private final JwtRoleParser jwtRoleParser;
    private final HttpServletRequest request;

    @Around("@annotation(roleCheck)")
    public Object checkRole(ProceedingJoinPoint joinPoint, RoleCheck roleCheck) throws Throwable {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.");
        }

        String jwt = token.replace("Bearer ", "");
        List<String> requiredRoles = Arrays.asList(roleCheck.value());
        String userRole = jwtRoleParser.getRole(jwt);

        if (requiredRoles.contains("ALL") || requiredRoles.contains(userRole)) {
            return joinPoint.proceed();
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
    }
}
