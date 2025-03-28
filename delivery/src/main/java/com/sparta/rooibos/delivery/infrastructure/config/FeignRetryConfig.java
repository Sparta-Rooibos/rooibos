package com.sparta.rooibos.delivery.infrastructure.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
public class FeignRetryConfig {
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(
            100, // 처음 재시도 간격 (밀리초) -> 0.1초
            SECONDS.toMillis(1), // 최대 재시도 간격 (밀리초) -> 1초
            3 // 최대 재시도 횟수 -> 3번까지 재시도
        );
    }
}
