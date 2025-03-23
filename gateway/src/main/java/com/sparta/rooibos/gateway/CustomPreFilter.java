package com.sparta.rooibos.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class CustomPreFilter implements GlobalFilter, Ordered {

    private final ReactiveStringRedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String key = exchange.getRequest().getHeaders().getFirst("Idempotency-Key");
        if (key == null || key.isBlank()) {
            return chain.filter(exchange); // 키 없으면 멱등성 확인하지 않음
        }

        String redisKey = "idempotency:" + key;

        return redisTemplate.opsForValue().get(redisKey)
                .defaultIfEmpty(null)
                .flatMap(existing -> {
                    if (existing != null) {
                        exchange.getResponse().setStatusCode(HttpStatus.CONFLICT);
                        return exchange.getResponse().setComplete();
                    }
                    return redisTemplate.opsForValue()
                            .set(redisKey, "processed", Duration.ofMinutes(5))
                            .then(chain.filter(exchange));
                });
    }

    @Override
    public int getOrder() {
        return -1; // JwtFilter보다 먼저 실행하려면 음수로
    }
}

