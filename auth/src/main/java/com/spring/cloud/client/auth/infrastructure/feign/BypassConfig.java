package com.spring.cloud.client.auth.infrastructure.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BypassConfig {

    @Bean
    public RequestInterceptor gatewayBypassHeaderInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                template.header("X-GATEWAY-AUTHORIZED", "true");
                template.header("X-User-Email", "internal-auth@rooibos.com");
                template.header("X-User-Role", "ROLE_SYSTEM");
            }
        };
    }
}

