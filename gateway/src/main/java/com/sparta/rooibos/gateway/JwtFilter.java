package com.sparta.rooibos.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter implements GlobalFilter, Ordered {
    private final WebClient webClient;
    private final ReactiveStringRedisTemplate redisTemplate;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String path = exchange.getRequest().getURI().getPath();
        log.info("Gateway 요청 경로: " + path);
        if (path.startsWith("/auth/api/v1/auth/login") || path.equals("/user/api/v1/user/signup")) {
            return chain.filter(exchange);
        }

        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authorization.split(" ")[1];

        return validateToken(token, exchange, chain);
    }

    private Mono<Void>  validateToken(String token, ServerWebExchange exchange,  GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm()))
                    .build().parseSignedClaims(token);
            Claims claims = claimsJws.getBody();
            String username = claims.get("username", String.class);
            String email = claims.get("email", String.class);
            String category = claims.get("category", String.class);
            String role = claims.get("role", String.class);
            Instant issuedAt = claims.getIssuedAt().toInstant();
            Instant expiredAt = claims.getExpiration().toInstant();

            if (!"access".equals(category)) {
                return response.setComplete();
            }

            if (Instant.now().isAfter(expiredAt)) {
                String refreshToken = exchange.getRequest().getCookies()
                        .getFirst("refreshToken") != null ?
                        exchange.getRequest().getCookies().getFirst("refreshToken").getValue() : null;

                if (refreshToken == null) {
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                }
                return webClient.post()
                        .uri("http://auth-service/api/v1/auth/reissue")
                        .cookie("refreshToken", refreshToken)
                        .exchangeToMono(reissueResponse -> {
                            if (reissueResponse.statusCode().is2xxSuccessful()) {
                                // 새 AccessToken 가져옴
                                String newAccessToken = reissueResponse.headers().header("Authorization").get(0);

                                // 요청 헤더 교체
                                ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken)
                                        .build();

                                return chain.filter(exchange.mutate().request(mutatedRequest).build());
                            } else {
                                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                                return response.setComplete();
                            }
                        });
            }

            String blacklistKey = "blacklist:" + email;
            return redisTemplate.opsForValue().get(blacklistKey)
                    .defaultIfEmpty("")
                    .flatMap(blacklistTimestamp -> {
                        if (!blacklistTimestamp.isEmpty()) {
                            Instant blacklistedAt = Instant.ofEpochSecond(Long.parseLong(blacklistTimestamp));
                            if (issuedAt.isBefore(blacklistedAt)) {
                                log.warn("블랙리스트 사용자 접근 차단됨");
                                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                                return response.setComplete();
                            }
                        }

                        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                .header("X-User-Name", username)
                                .header("X-User-Email", email)
                                .header("X-User-Role", role)
                                .build();

                        return chain.filter(exchange.mutate().request(mutatedRequest).build());

                    });
        }catch (Exception e) {
            log.error("토큰 검증 중 예외 발생: {}", e.getMessage(), e);
            return response.setComplete();
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}