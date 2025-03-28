package com.sparta.rooibos.hub.application.dto.hub.request;

public record SearchHubRequest(
        String name,
        String region,
        int page,
        int size
) {}
