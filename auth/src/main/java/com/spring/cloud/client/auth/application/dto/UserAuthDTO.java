package com.spring.cloud.client.auth.application.dto;

public record UserAuthDTO(
        String username,
        String email,
        String password,
        String role
){}

