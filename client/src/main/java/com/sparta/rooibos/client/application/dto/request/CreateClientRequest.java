package com.sparta.rooibos.client.application.dto.request;

public record CreateClientRequest(String name, String clientType, String managedHubId, String address) {}