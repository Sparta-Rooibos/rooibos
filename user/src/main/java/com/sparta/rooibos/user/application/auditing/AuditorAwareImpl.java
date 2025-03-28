package com.sparta.rooibos.user.application.auditing;

import com.sparta.rooibos.user.application.auditing.UserAuditorContext;
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

