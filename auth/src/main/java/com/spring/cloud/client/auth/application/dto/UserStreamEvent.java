package com.spring.cloud.client.auth.application.dto;

public record UserStreamEvent(
        String eventType,
        Object payload
) {}

