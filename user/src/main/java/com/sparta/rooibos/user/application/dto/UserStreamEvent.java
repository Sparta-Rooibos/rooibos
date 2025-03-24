package com.sparta.rooibos.user.application.dto;

public record UserStreamEvent(
        String eventType,
        Object payload
) {}

