package com.sparta.rooibos.auth.application.dto;

public record UserAuthDTO(
        String username,
        String email,
        String password,
        String role
){}

