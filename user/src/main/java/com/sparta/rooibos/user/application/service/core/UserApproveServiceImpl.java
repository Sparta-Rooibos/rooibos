package com.sparta.rooibos.user.application.service.core;

import com.sparta.rooibos.user.application.dto.UserStreamRequest;
import com.sparta.rooibos.user.application.exception.BusinessUserException;
import com.sparta.rooibos.user.application.exception.custom.UserErrorCode;
import com.sparta.rooibos.user.application.service.port.EventProvider;
import com.sparta.rooibos.user.application.service.port.UserApproveService;
import com.sparta.rooibos.user.domain.entity.User;
import com.sparta.rooibos.user.domain.repository.UserRepository;
import com.sparta.rooibos.user.infrastructure.auditing.UserAuditorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserApproveServiceImpl implements UserApproveService {
    private final UserRepository userRepository;
    private final EventProvider eventProvider;

    @Transactional
    public void approveUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessUserException(UserErrorCode.USER_NOT_FOUND));

        String approver = UserAuditorContext.getRole();
        String pending = user.getRole().getAuthority();
        log.info("Approver Role = {}", approver);
        log.info("Pending User Role (Authority) = {}", pending);

        if (!approver.equals("ROLE_MASTER") && !approver.equals(pending)) {
            throw new BusinessUserException(UserErrorCode.FORBIDDEN_USER_ACCESS);
        }

        user.approve();
        userRepository.save(user);
        eventProvider.sendUserInfo(UserStreamRequest.fromEntity(user));
    }

    @Transactional
    public void rejectUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessUserException(UserErrorCode.USER_NOT_FOUND));

        String approverRole = UserAuditorContext.getRole();
        String pendingRole = user.getRole().getAuthority();

        if (!approverRole.equals("ROLE_MASTER") && !approverRole.equals(pendingRole)) {
            throw new BusinessUserException(UserErrorCode.FORBIDDEN_USER_ACCESS);
        }

        user.reject();
        userRepository.save(user);
    }
}
