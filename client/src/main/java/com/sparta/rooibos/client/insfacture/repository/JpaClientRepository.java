package com.sparta.rooibos.client.insfacture.repository;

import com.sparta.rooibos.client.domain.entity.Client;
import com.sparta.rooibos.client.domain.repository.ClientRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaClientRepository extends JpaRepository<Client, Long>, ClientRepository {
}
