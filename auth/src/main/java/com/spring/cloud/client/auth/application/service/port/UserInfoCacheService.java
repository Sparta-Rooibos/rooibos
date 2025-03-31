package com.spring.cloud.client.auth.application.service.port;

import com.spring.cloud.client.auth.application.dto.CachedUserResponse;

public interface UserInfoCacheService {
    CachedUserResponse getUserForAuth(String email);
}
