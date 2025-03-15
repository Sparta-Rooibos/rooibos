package com.spring.cloud.client.auth.application.dto;

public record UserDTO (
        String requestId,  // Kafka 요청 ID (비동기 응답 매칭용)
        String username,   // 사용자 ID
        String password,   // 암호화된 비밀번호
        String role       // 사용자 역할 (예: ROLE_USER, ROLE_ADMIN)
){}

