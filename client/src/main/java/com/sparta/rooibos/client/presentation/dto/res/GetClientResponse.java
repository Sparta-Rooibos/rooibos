package com.sparta.rooibos.client.presentation.dto.res;


import com.sparta.rooibos.client.application.dto.res.GetClientApplicationResponse;

import java.time.LocalDateTime;

public record GetClientResponse(String id, String name, String address, String type, String managedHubId,
                                LocalDateTime createdAt, LocalDateTime updatedAt) {
    public GetClientResponse(GetClientApplicationResponse client) {
        this(client.id(), client.name(), client.address(), client.type(), client.managedHubId(), client.createdAt(), client.updatedAt());
    }

}
