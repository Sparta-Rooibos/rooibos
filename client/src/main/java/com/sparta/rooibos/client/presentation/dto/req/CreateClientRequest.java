package com.sparta.rooibos.client.presentation.dto.req;

public record CreateClientRequest(String name, String clientType, String managedHubId, String address) {}