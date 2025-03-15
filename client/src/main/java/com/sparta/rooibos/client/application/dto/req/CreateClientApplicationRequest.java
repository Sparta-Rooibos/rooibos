package com.sparta.rooibos.client.application.dto.req;

public record CreateClientApplicationRequest(String name, String clientType, String managedHubId, String address) {}