package com.sparta.rooibos.client.application.feigin.service;

import com.sparta.rooibos.client.domain.fegin.hub.model.Hub;

import java.util.Optional;

public interface HubService {
    Optional<Hub> getHub(String hubId);
}
