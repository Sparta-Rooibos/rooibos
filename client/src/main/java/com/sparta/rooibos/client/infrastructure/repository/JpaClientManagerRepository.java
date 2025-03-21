package com.sparta.rooibos.client.infrastructure.repository;

import com.sparta.rooibos.client.domain.entity.ClientManager;
import com.sparta.rooibos.client.domain.repository.ClientManagerRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaClientManagerRepository extends JpaRepository<ClientManager, UUID>, ClientManagerRepository {

}
