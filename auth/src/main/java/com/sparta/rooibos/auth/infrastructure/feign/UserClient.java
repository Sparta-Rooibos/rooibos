package com.sparta.rooibos.auth.infrastructure.feign;

import com.sparta.rooibos.auth.application.dto.response.CachedUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user-service",
        configuration = BypassConfig.class)
public interface UserClient {
    @GetMapping("/api/v1/user/internal/{email}")
    CachedUserResponse getUserForAuth(@PathVariable String email);
}