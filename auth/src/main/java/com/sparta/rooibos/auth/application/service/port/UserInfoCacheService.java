package com.sparta.rooibos.auth.application.service.port;


import com.sparta.rooibos.auth.application.dto.response.CachedUserResponse;

public interface UserInfoCacheService {
    CachedUserResponse getUserForAuth(String email);
}