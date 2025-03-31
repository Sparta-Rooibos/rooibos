package com.spring.cloud.client.auth.infrastructure.feign;

import com.spring.cloud.client.auth.application.dto.CachedUserResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "user-service",
        configuration = BypassConfig.class)
public interface UserClient {
//    @Cacheable(cacheNames = "user_info", key = "#email")
    @GetMapping("/api/v1/user/internal/{email}")
    CachedUserResponse getUserForAuth(@PathVariable String email);
}
