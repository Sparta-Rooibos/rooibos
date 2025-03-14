package com.spring.cloud.client.auth.application.service;

import com.spring.cloud.client.auth.infrastructure.jwt.JwtValidator;
import com.spring.cloud.client.auth.infrastructure.redis.BlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final JwtValidator jwtValidator;
    private final BlacklistService blacklistService;

    public boolean authorizeAction(String token, String requestedUser) {
        if (token == null) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }

        if (blacklistService.isTokenBlacklisted(token)) {
            throw new RuntimeException("보안 조치로 인해 접근이 제한되었습니다.");
        }

        String role = jwtValidator.getRole(token);

        // Master는 모든 요청 가능
        if ("ROLE_MASTER".equals(role)) {
            return true;
        }

        // 자기 자신만 접근 가능 (HUB, DELIVERY, CLIENT)
        if (role.equals(requestedUser)) {
            return true;
        }

        // 권한 없음
        throw new RuntimeException("권한이 없습니다.");
    }
}

///**
// * 🔹 주문 조회 권한 확인
// */
//public boolean canViewOrders(String token, String requestedUser) {
//    TokenValidationResponse userInfo = validateToken(token);
//
//    // ✅ Master는 모든 주문 조회 가능
//    if ("ROLE_MASTER".equals(userInfo.getRole())) {
//        return true;
//    }
//
//    // ✅ 일반 유저는 자기 주문만 조회 가능
//    return userInfo.getUsername().equals(requestedUser);
//}
//
///**
// * 🔹 주문 생성 권한 확인 (클라이언트만 가능)
// */
//public boolean canCreateOrder(String token) {
//    TokenValidationResponse userInfo = validateToken(token);
//
//    // ✅ 클라이언트만 주문 생성 가능
//    return "ROLE_CLIENT".equals(userInfo.getRole());
//}
//
///**
// * 🔹 주문 취소 권한 확인 (클라이언트 & 허브 가능)
// */
//public boolean canCancelOrder(String token) {
//    TokenValidationResponse userInfo = validateToken(token);
//
//    // ✅ 클라이언트 또는 허브만 주문 취소 가능
//    return "ROLE_CLIENT".equals(userInfo.getRole()) || "ROLE_HUB".equals(userInfo.getRole());
//}