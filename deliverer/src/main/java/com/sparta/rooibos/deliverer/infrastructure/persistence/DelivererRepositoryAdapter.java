package com.sparta.rooibos.deliverer.infrastructure.persistence;

import com.sparta.rooibos.deliverer.application.dto.request.DelivererSearchRequest;
import com.sparta.rooibos.deliverer.domain.entity.Deliverer;
import com.sparta.rooibos.deliverer.domain.repository.DelivererRepository;
import com.sparta.rooibos.deliverer.domain.repository.DelivererRepositoryCustom;
import com.sparta.rooibos.deliverer.domain.model.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DelivererRepositoryAdapter implements DelivererRepository {

    private final JpaDelivererRepository jpaDelivererRepository;
    private final DelivererRepositoryCustom delivererRepositoryCustom;

    @Override
    public Deliverer save(Deliverer deliverer) {
        return jpaDelivererRepository.save(deliverer);
    }

    @Override
    public int countByHubIdAndHiddenFalse(UUID hubId) {
        return jpaDelivererRepository.countByHubIdAndHiddenFalse(hubId);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaDelivererRepository.existsById(id);
    }

    @Override
    public Optional<Deliverer> findById(UUID id) {
        return jpaDelivererRepository.findById(id);
    }

    @Override
    public int findMaxOrderByHubId(UUID hubId) {
        return jpaDelivererRepository.findMaxOrderByHubId(hubId);
    }

    public Pagination<Deliverer> searchDeliverers(DelivererSearchRequest request) {
        return delivererRepositoryCustom.searchDeliverers(request);
    }
}