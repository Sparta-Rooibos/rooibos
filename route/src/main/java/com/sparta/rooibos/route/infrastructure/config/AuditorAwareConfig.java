package com.sparta.rooibos.route.infrastructure.config;

import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class AuditorAwareConfig implements AuditorAware<String> {
    @Override
    public @NonNull Optional<String> getCurrentAuditor() {
        return Optional.of("admin");
    }
}

