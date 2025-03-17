package com.spring.cloud.client.user.infrastructure.persistence;

import com.spring.cloud.client.user.domain.entity.User;
import com.spring.cloud.client.user.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaUserRepository extends JpaRepository<User, UUID>, UserRepository {
}

