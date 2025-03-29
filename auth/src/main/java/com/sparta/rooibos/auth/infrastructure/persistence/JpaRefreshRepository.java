package com.sparta.rooibos.auth.infrastructure.persistence;

import com.sparta.rooibos.auth.domain.entity.Refresh;
import com.sparta.rooibos.auth.domain.repository.RefreshRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaRefreshRepository extends JpaRepository<Refresh, UUID>, RefreshRepository {
}
