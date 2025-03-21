package com.sparta.rooibus.delivery.application.dto.request.feign.user;

import java.util.UUID;

public record GetUserRequest(UUID userId) {

    public static GetUserRequest from(UUID recipient) {
        return new GetUserRequest(recipient);
    }
}
