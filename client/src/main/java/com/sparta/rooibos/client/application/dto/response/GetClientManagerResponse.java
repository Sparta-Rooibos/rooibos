package com.sparta.rooibos.client.application.dto.response;

import com.sparta.rooibos.client.domain.entity.ClientManager;

public record GetClientManagerResponse(
        String userId
) {
    public static GetClientManagerResponse get(ClientManager clientManager) {
        return new GetClientManagerResponse(clientManager.getUserId());
    }
}
