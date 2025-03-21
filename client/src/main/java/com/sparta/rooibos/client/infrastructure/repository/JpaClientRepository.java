package com.sparta.rooibos.client.infrastructure.repository;

import com.sparta.rooibos.client.domain.entity.Client;
import com.sparta.rooibos.client.domain.repository.ClientRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaClientRepository extends JpaRepository<Client, UUID>, ClientRepository {}
