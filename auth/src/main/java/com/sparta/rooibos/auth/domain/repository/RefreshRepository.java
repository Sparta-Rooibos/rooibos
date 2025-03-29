package com.sparta.rooibos.auth.domain.repository;

import com.sparta.rooibos.auth.domain.entity.Refresh;

public interface RefreshRepository {
    boolean deleteByRefresh(String refresh);
    Refresh save(Refresh refresh);
    void deleteByEmail(String email);
}
