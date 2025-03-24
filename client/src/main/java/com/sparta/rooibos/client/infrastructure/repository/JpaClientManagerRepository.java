package com.sparta.rooibos.client.infrastructure.repository;

import com.sparta.rooibos.client.domain.entity.ClientManager;
import com.sparta.rooibos.client.domain.repository.ClientManagerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface JpaClientManagerRepository extends JpaRepository<ClientManager, UUID>, ClientManagerRepository {
    @Query("select c.client.id from ClientManager c where c.userId = :email and c.client.deleteBy is null")
    String getClientIdByUserId(@Param("email") String email);
}
