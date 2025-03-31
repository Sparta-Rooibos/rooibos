package com.sparta.rooibos.deliverer.domain.repository;

import com.sparta.rooibos.deliverer.application.dto.request.DelivererSearchRequest;
import com.sparta.rooibos.deliverer.domain.entity.Deliverer;
import com.sparta.rooibos.deliverer.domain.entity.DelivererType;
import com.sparta.rooibos.deliverer.domain.model.Pagination;

import java.util.Optional;
import java.util.UUID;

public interface DelivererRepository {
    Deliverer save(Deliverer deliverer);
    int countByHubIdAndHiddenFalse(UUID hubId);
    boolean existsById(UUID id);
    Optional<Deliverer> findById(UUID id);
    int findMaxOrderByHubId(UUID hubId);
    Pagination<Deliverer> searchDeliverers(DelivererSearchRequest request);
    int countByHubIdAndTypeAndHiddenFalse(UUID hubId, DelivererType type);
    Optional<Deliverer> findNextAvailableDeliverer(UUID hubId, DelivererType type);
}
