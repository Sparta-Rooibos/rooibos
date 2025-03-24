package com.sparta.rooibos.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomPostFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long start = System.currentTimeMillis();
        return chain.filter(exchange).doFinally(signal -> {
            long duration = System.currentTimeMillis() - start;
            log.info("요청 " + exchange.getRequest().getURI() + " 처리 시간: " + duration + "ms");
        });
    }

    @Override
    public int getOrder() {
        return 1; // JwtFilter 이후 실행
    }
}