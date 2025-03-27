package com.sparta.rooibos.user.domain.repository;

import com.sparta.rooibos.user.application.dto.request.UserSearchRequest;
import com.sparta.rooibos.user.domain.entity.User;
import com.sparta.rooibos.user.domain.model.Pagination;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    User save(User user);

    Optional<User> findById(UUID id);

    void deleteById(UUID id);

    Pagination<User> searchUsers(UserSearchRequest request);
}
