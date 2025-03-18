package com.spring.cloud.client.auth.domain.repository;

import com.spring.cloud.client.auth.domain.entity.Refresh;

public interface RefreshRepository {
    boolean deleteByRefresh(String refresh);
    Refresh save(Refresh refresh);
    boolean deleteByEmail(String email);
}
