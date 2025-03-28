package com.sparta.rooibos.route.presentation.adapter.in.message;

import java.time.LocalDateTime;
import java.util.UUID;

public record HubDeletedActivity(
        UUID hubId,
        String region,
        LocalDateTime deletedAt
) {}
