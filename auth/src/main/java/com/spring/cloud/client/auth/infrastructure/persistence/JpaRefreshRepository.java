package com.spring.cloud.client.auth.infrastructure.persistence;

import com.spring.cloud.client.auth.domain.entity.Refresh;
import com.spring.cloud.client.auth.domain.repository.RefreshRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaRefreshRepository extends JpaRepository<Refresh, UUID>, RefreshRepository {
}
