package com.sparta.rooibos.user.application.service.core;

import com.sparta.rooibos.user.application.dto.UserAuthDTO;
import com.sparta.rooibos.user.application.dto.request.UserRequest;
import com.sparta.rooibos.user.application.dto.response.UserResponse;
import com.sparta.rooibos.user.application.exception.BusinessUserException;
import com.sparta.rooibos.user.application.exception.custom.UserErrorCode;
import com.sparta.rooibos.user.application.service.EventProvider;
import com.sparta.rooibos.user.application.service.port.UserService;
import com.sparta.rooibos.user.domain.entity.User;
import com.sparta.rooibos.user.domain.repository.UserRepository;
import com.sparta.rooibos.user.infrastructure.auditing.UserAuditorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EventProvider eventProvider;

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        boolean isExist = userRepository.existsByEmail(userRequest.email());
        if (isExist) {
            throw new BusinessUserException(UserErrorCode.DUPLICATE_EMAIL);
        }

        User user = User.create(
                userRequest.username(),
                userRequest.email(),
                passwordEncoder.encode(userRequest.password()),
                userRequest.slackAccount(),
                userRequest.phone(),
                userRequest.role()
        );

        userRepository.save(user);

        return UserResponse.from(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getUser() {
        String email = UserAuditorContext.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessUserException(UserErrorCode.USER_NOT_FOUND));

        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse updateUser(UserRequest userRequest) {
        String email = UserAuditorContext.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessUserException(UserErrorCode.USER_NOT_FOUND));

        user.updateUser(
                userRequest.email(),
                userRequest.slackAccount(),
                passwordEncoder.encode(userRequest.password()),
                userRequest.phone(),
                userRequest.role()
        );

        User updatedUser = userRepository.save(user);
        eventProvider.sendUserInfo(UserAuthDTO.fromEntity(updatedUser));

        return UserResponse.from(updatedUser);
    }

    @Transactional
    public void deleteUser() {
        String email = UserAuditorContext.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessUserException(UserErrorCode.USER_NOT_FOUND));

        user.delete(email);
        userRepository.save(user);
        eventProvider.sendUserDeleteInfo(email);
    }

    @Transactional
    public void reportUser() {
        String email = UserAuditorContext.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessUserException(UserErrorCode.USER_NOT_FOUND));

        eventProvider.sendUserReportInfo(email);
    }
}