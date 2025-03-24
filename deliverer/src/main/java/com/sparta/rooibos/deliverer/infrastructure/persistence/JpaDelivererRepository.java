package com.sparta.rooibos.deliverer.infrastructure.persistence;

import com.sparta.rooibos.deliverer.domain.entity.Deliverer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaDelivererRepository extends JpaRepository<Deliverer, UUID> {
    Optional<Deliverer> findById(UUID id);
    @Query("SELECT COALESCE(MAX(d.order), -1) FROM Deliverer d WHERE d.hubId = :hubId AND d.hidden = false")
    int findMaxOrderByHubId(UUID hubId);
    int countByHubIdAndHiddenFalse(UUID hubId);
}
