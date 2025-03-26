package com.sparta.rooibos.user.application.service.port;

import com.sparta.rooibos.user.application.dto.request.UserRequest;
import com.sparta.rooibos.user.application.dto.request.UserSearchRequest;
import com.sparta.rooibos.user.application.dto.request.UserUpdateRequest;
import com.sparta.rooibos.user.application.dto.response.UserListResponse;
import com.sparta.rooibos.user.application.dto.response.UserResponse;

import java.util.UUID;

public interface MasterService {
    UserResponse createUserByMaster(UserRequest userRequest);
    UserResponse getUserByMaster(UUID userId);
    UserResponse updateUserByMaster(UUID userId, UserUpdateRequest request);
    UserListResponse searchUsersByMaster(UserSearchRequest request);
    void deleteUserByMaster(UUID userId);
    void reportUserByMaster(UUID userId);
}
