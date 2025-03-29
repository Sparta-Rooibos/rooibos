package com.sparta.rooibos.hub.application.dto.hubManager.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateHubManagerRequest(
        @NotNull UUID userId,
        @NotNull UUID hubId
) {}
