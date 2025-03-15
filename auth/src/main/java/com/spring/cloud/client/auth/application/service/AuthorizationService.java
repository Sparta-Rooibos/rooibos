//package com.spring.cloud.client.auth.application.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class AuthorizationService {
//    private final JwtProvider jwtProvider;
//    private final RedisProvider redisProvider;
//
//    public boolean authorizeAction(String token, List<String> allowedRoles) {
//        if (token == null) {
//            throw new RuntimeException("유효하지 않은 토큰입니다.");
//        }
//
//        if (redisProvider.isTokenBlacklisted(token)) {
//            throw new RuntimeException("보안 조치로 인해 접근이 제한되었습니다.");
//        }
//
//        String role = jwtProvider.getRole(token);
//
//        if (allowedRoles.contains(role)) {
//            return true;
//        }
//
//        throw new RuntimeException("권한이 없습니다.");
//    }
//}