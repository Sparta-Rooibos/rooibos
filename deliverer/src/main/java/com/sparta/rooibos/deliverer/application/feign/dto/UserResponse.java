package com.sparta.rooibos.deliverer.application.feign.dto;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String email,
        String slackAccount,
        String phone,
        String role
) {}
