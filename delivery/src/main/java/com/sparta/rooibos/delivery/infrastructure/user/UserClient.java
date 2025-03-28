package com.sparta.rooibos.delivery.infrastructure.user;

import com.sparta.rooibos.delivery.application.dto.response.feign.user.GetUserResponse;
import com.sparta.rooibos.delivery.application.service.feign.UserService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@Primary
@FeignClient(name = "user-service", url = "http://localhost:19093")
public interface UserClient extends UserService {

    @GetMapping("/api/v1/user/master/{userId}")
    ResponseEntity<GetUserResponse> getUser(
        @RequestHeader("X-User-Role") String role,
        @PathVariable("userId") UUID userId
    );
}
