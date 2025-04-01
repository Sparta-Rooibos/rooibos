package com.sparta.rooibos.deliverer.application.feign;

import com.sparta.rooibos.deliverer.application.feign.dto.UserResponse;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface UserService {
    UserResponse getUserByMaster(@PathVariable UUID userId);
}
