package com.sparta.rooibos.client.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateClientRequest(@NotNull String name,
                                  @NotNull String clientType,
                                  @NotNull String managedHubId,
                                  @NotNull String address) {}