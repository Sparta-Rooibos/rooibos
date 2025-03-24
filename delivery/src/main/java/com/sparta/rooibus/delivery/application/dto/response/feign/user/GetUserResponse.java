package com.sparta.rooibus.delivery.application.dto.response.feign.user;

import java.util.UUID;

public record GetUserResponse(
    UUID id,
    String username,
    String email,
    String slackAccount,
    String phone,
    String role
) {}
