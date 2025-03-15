package com.spring.cloud.client.auth.application.service;

public interface JwtProvider {
    String createJwt(String type, String username, String role, long expiration);
    boolean isExpired(String token);
    String getCategory(String token);
    String getUsername(String token);
    String getRole(String token);
}
