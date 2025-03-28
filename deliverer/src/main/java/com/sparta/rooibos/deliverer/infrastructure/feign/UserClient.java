package com.sparta.rooibos.deliverer.infrastructure.feign;

import com.sparta.rooibos.deliverer.application.feign.UserService;
import com.sparta.rooibos.user.application.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service")
public interface UserClient extends UserService {

    @GetMapping("/api/v1/user/master/{userId}")
    UserResponse getUserByMaster(@PathVariable(value = "userId") UUID userId);
}
