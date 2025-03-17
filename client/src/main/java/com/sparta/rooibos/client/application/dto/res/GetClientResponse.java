package com.sparta.rooibos.client.application.dto.res;


import java.time.LocalDateTime;

public record GetClientResponse(String id, String name, String address, String type, String managedHubId, LocalDateTime createdAt, LocalDateTime updatedAt) {}
