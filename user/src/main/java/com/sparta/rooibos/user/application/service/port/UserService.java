package com.sparta.rooibos.user.application.service.port;

import com.sparta.rooibos.user.application.dto.request.UserRequest;
import com.sparta.rooibos.user.application.dto.request.UserUpdateRequest;
import com.sparta.rooibos.user.application.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    UserResponse getUser();
    UserResponse updateUser(UserUpdateRequest request);
    void deleteUser();
    void reportUser();
}
