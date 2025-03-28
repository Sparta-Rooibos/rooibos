package com.spring.cloud.client.auth.application.dto;

public record AuthStreamResponse(
        String username,
        String email,
        String password,
        String role
){}

