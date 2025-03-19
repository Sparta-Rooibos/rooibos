package com.spring.cloud.client.auth.application.service.port;

public interface JwtProvider {
    String createJwt(String type, String email, String role, long expiration);
    boolean isExpired(String token);
    String getCategory(String token);
    String getEmail(String token);
    String getRole(String token);
}
