package com.spring.cloud.client.auth.infrastructure.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service")
public interface AuthClient {

    @GetMapping("/api/v1/auth/authorize")
    Boolean authorize(
            @RequestHeader("Authorization") String token,
            @RequestParam("requestedUser") String requestedUser
    );
}
