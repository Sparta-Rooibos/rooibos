package com.sparta.rooibos.user.infrastructure.persistence;

import com.sparta.rooibos.user.domain.entity.User;
import com.sparta.rooibos.user.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaUserRepository extends JpaRepository<User, UUID>, UserRepository {
}
