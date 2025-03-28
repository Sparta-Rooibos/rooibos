package com.sparta.rooibos.auth.application.dto;

public record UserStreamEvent(
        String eventType,
        Object payload
) {}

