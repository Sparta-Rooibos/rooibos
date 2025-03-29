package com.sparta.rooibos.delivery.application.service.feign;

import com.sparta.rooibos.delivery.application.dto.response.feign.user.GetUserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface UserService {
    ResponseEntity<GetUserResponse> getUser(
        String role,
        UUID userId);
}
