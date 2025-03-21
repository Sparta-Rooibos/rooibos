package com.sparta.rooibus.delivery.application.dto.response.feign.user;

public record GetUserResponse(
    String userName,
    String email,
    String phone,
    String slackAccount,
    String role
) {}
