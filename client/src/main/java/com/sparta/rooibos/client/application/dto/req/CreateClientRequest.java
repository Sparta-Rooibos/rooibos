package com.sparta.rooibos.client.application.dto.req;

public record CreateClientRequest(String name, String clientType, String managedHubId, String address) {}