package com.sparta.rooibos.deliverer.infrastructure.feign;

import com.sparta.rooibos.deliverer.infrastructure.feign.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/v1/user/master/{userId}")
    UserResponse getUserByMaster(@PathVariable UUID userId);
}
