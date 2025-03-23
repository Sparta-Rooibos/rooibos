package com.sparta.rooibos.product.application.feign.dto.response;

import com.sparta.rooibos.product.domain.feign.dto.ManageHub;

public record ManageHubResponse(String id,
                                String name) {
    public ManageHubResponse(ManageHub hub) {
        this(hub.id(), hub.name());
    }
}
