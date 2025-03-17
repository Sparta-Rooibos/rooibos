package com.spring.cloud.client.user.domain.repository;

import com.spring.cloud.client.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    User save(User user);
    Optional<User> findById(UUID id);
    void deleteById(UUID id);
}

