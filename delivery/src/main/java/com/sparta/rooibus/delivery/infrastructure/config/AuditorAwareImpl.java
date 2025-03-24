package com.sparta.rooibus.delivery.infrastructure.config;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
