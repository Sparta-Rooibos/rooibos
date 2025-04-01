package com.sparta.rooibos.auth.infrastructure.persistence;

import com.sparta.rooibos.auth.domain.repository.RefreshRepositoryCustom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshRepositoryImpl implements RefreshRepositoryCustom {

    private final EntityManager entityManager;

    @Override
    public void flush() {
        entityManager.flush();
    }
}
