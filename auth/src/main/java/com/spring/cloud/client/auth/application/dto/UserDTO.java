package com.spring.cloud.client.auth.application.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String requestId;  // Kafka 요청 ID (비동기 응답 매칭용)
    private String username;   // 사용자 ID
    private String password;   // 암호화된 비밀번호
    private String role;       // 사용자 역할 (예: ROLE_USER, ROLE_ADMIN)
}

