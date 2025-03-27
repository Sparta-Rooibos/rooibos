package com.sparta.rooibus.order.infrastructure.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(getUserFromHeader());
    }

    private String getUserFromHeader() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
            .map(attrs -> ((ServletRequestAttributes) attrs).getRequest().getHeader("X-User-Email"))
            .orElse("system"); // 기본값 설정
    }
}
