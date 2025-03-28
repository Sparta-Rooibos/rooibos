package com.sparta.rooibos.user.application.aop;

import com.sparta.rooibos.user.application.exception.BusinessUserException;
import com.sparta.rooibos.user.application.exception.custom.UserErrorCode;
import com.sparta.rooibos.user.application.auditing.UserAuditorContext;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RoleCheckAspect {

    @Pointcut("@annotation(com.sparta.rooibos.user.infrastructure.aop.MasterOnlyCheck)")
    public void masterOnly() {}

    @Around("masterOnly()")
    public Object checkMaster(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!"ROLE_MASTER".equalsIgnoreCase(UserAuditorContext.getRole())) {
            throw new BusinessUserException(UserErrorCode.FORBIDDEN_USER_ACCESS);
        }
        return joinPoint.proceed();
    }
}