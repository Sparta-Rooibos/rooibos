package com.spring.cloud.client.auth.application.dto;

public record UserDTO (
        String username,
        String email,
        String password,
        String role
){}

