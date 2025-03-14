package com.sparta.rooibos.client.application.service.dto.res;

import java.time.LocalDateTime;

public record GetClientApplicationResponse(String id, String name, String address, String type, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
