package com.spring.cloud.client.user.application.service;

import com.spring.cloud.client.user.application.dto.UserAuthDTO;
import com.spring.cloud.client.user.application.dto.UserRequest;
import com.spring.cloud.client.user.domain.entity.User;
import com.spring.cloud.client.user.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EventProvider eventProvider;

    @Transactional
    public ResponseEntity<?> createUser(UserRequest userRequest) {
        boolean isExist = userRepository.existsByEmail(userRequest.email());
        if (isExist) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
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
        eventProvider.sendUserInfo(UserAuthDTO.fromEntity(user));

        return ResponseEntity.ok("유저가 생성되었습니다.");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다."));

        return ResponseEntity.ok("유저가 조회되었습니다.");
    }

    @Transactional
    public ResponseEntity<?> updateUser(UUID userId, UserRequest userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다."));

        user.updateUser(
                userRequest.email(),
                userRequest.slackAccount(),
                passwordEncoder.encode(userRequest.password()),
                userRequest.phone(),
                userRequest.role()
        );

        User updatedUser = userRepository.save(user);
        eventProvider.sendUserInfo(UserAuthDTO.fromEntity(updatedUser));

        return ResponseEntity.ok("유저 정보가 업데이트되었습니다.");
    }

    @Transactional
    public ResponseEntity<?> deleteUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다."));
        String email = user.getEmail();
//        user.delete(email);
        userRepository.save(user);
        eventProvider.sendUserDeleteInfo(email);

        return ResponseEntity.ok("유저가 삭제되었습니다.");
    }

    @Transactional
    public ResponseEntity<?> reportUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다."));
        String email = user.getEmail();

        eventProvider.sendUserReportInfo(email);

        return ResponseEntity.ok("유저가 신고되었습니다.");
    }
}
