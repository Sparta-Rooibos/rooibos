package com.sparta.rooibos.client.application.feigin.service.dto;

import com.sparta.rooibos.client.domain.fegin.hub.model.Hub;

public record ManageHub(String id,
                        String name,
                        String region,
                        String address) {

    public ManageHub(Hub hub) {
        this(hub.hubId(), hub.name(), hub.region(), hub.address());
    }
}
