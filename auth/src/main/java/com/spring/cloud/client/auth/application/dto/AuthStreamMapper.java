package com.spring.cloud.client.auth.application.dto;

public class AuthStreamMapper {
    public static AuthStreamResponse toResponse(AuthStreamRequest request) {
        return new AuthStreamResponse(
                request.email(),
                request.username(),
                request.password(),
                request.role()
        );
    }
}

