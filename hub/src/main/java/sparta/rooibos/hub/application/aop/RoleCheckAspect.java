package sparta.rooibos.hub.application.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class RoleCheckAspect {

    @Around("@annotation(roleCheck)")
    public Object validateRole(ProceedingJoinPoint joinPoint, RoleCheck roleCheck) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            throw new IllegalStateException("요청 헤더 가져오기 실패");
        }

        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String requestRole = request.getHeader("X-User-Role");

        List<String> allowedRoles = Arrays.asList(roleCheck.value());
        if (!allowedRoles.contains(requestRole)) {
            throw new SecurityException(requestRole + ": 요청 권한이 없습니다.");
        }

        return joinPoint.proceed();
    }
}
