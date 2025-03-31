package com.spring.cloud.client.auth.application.dto;

import java.io.Serializable;

public record CachedUserResponse (
        String username,
        String email,
        String password,
        String role,
        String stauts
)implements Serializable{}

