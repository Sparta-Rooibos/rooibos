package com.sparta.rooibos.deliverer.infrastructure.auditing;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
         return Optional.of(UserAuditorContext.getEmail());
    }
}

