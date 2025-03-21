package com.sparta.rooibos.client.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateHubIdRequest(@NotNull String hubId) {}
