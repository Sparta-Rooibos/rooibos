package com.spring.cloud.client.auth.application.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)

public record AuthStreamRequest(
        String eventType,
        String email,
        String username,
        String password,
        String role
) {}

