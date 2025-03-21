package com.sparta.rooibus.delivery.application.service.feign;

import com.sparta.rooibus.delivery.application.dto.request.feign.user.GetUserRequest;
import com.sparta.rooibus.delivery.application.dto.response.feign.user.GetUserResponse;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    GetUserResponse getUser(GetUserRequest request);
}
