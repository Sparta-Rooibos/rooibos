package com.sparta.rooibos.user.application.service.core;

import com.sparta.rooibos.user.application.dto.request.UserRequest;
import com.sparta.rooibos.user.application.dto.request.UserSearchRequest;
import com.sparta.rooibos.user.application.dto.request.UserUpdateRequest;
import com.sparta.rooibos.user.application.dto.response.UserListResponse;
import com.sparta.rooibos.user.application.dto.response.UserResponse;
import com.sparta.rooibos.user.application.exception.BusinessUserException;
import com.sparta.rooibos.user.application.exception.custom.UserErrorCode;
import com.sparta.rooibos.user.application.service.port.BlacklistProvider;
import com.sparta.rooibos.user.application.service.port.MasterService;
import com.sparta.rooibos.user.domain.entity.User;
import com.sparta.rooibos.user.domain.entity.UserRoleStatus;
import com.sparta.rooibos.user.domain.model.Pagination;
import com.sparta.rooibos.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MasterServiceImpl implements MasterService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final BlacklistProvider blacklistProvider;

    @Transactional
    public UserResponse createUserByMaster(UserRequest userRequest) {
        boolean isExist = userRepository.existsByEmail(userRequest.email());
        if (isExist) {
            throw new BusinessUserException(UserErrorCode.DUPLICATE_EMAIL);
        }

        User user = User.createByMaster(
                userRequest.username(),
                userRequest.email(),
                passwordEncoder.encode(userRequest.password()),
                userRequest.slackAccount(),
                userRequest.phone(),
                userRequest.role(),
                UserRoleStatus.ACTIVE
        );

        userRepository.save(user);

        return UserResponse.from(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserByMaster(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessUserException(UserErrorCode.USER_NOT_FOUND));

        return UserResponse.from(user);
    }

    @CacheEvict(cacheNames = "user_info", keyGenerator = "auditorKeyGenerator")
    @Transactional
    public UserResponse updateUserByMaster(UUID userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessUserException(UserErrorCode.USER_NOT_FOUND));

        user.update(
                request.slackAccount(),
                passwordEncoder.encode(request.password()),
                request.phone()
        );

        User updatedUser = userRepository.save(user);

        return UserResponse.from(updatedUser);
    }

    @Transactional(readOnly = true)
    public UserListResponse searchUsersByMaster(UserSearchRequest request) {
        Pagination<User> resultPage = userRepository.searchUsers(request.toCriteria());
        return UserListResponse.from(resultPage);
    }

    @CacheEvict(cacheNames = "user_info", keyGenerator = "auditorKeyGenerator")
    @Transactional
    public void deleteUserByMaster(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessUserException(UserErrorCode.USER_NOT_FOUND));

        user.delete(user.getEmail());
        userRepository.save(user);
    }

    @CacheEvict(cacheNames = "user_info", keyGenerator = "auditorKeyGenerator")
    @Transactional
    public void reportUserByMaster(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessUserException(UserErrorCode.USER_NOT_FOUND));

        blacklistProvider.addToBlacklist(user.getEmail(), 86400000L);
    }
}