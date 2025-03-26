package com.sparta.rooibos.user.presentation.controller;

import com.sparta.rooibos.user.application.service.port.UserApproveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserApproveController {
    private final UserApproveService userApproveService;

    @PatchMapping("/approve/{userId}")
    public ResponseEntity<Void> approveUser(@PathVariable UUID userId) {
        log.info("🔥 Approve API 진입: {}", userId);
        userApproveService.approveUser(userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/reject/{userId}")
    public ResponseEntity<Void> rejectUser(@PathVariable UUID userId) {
        userApproveService.rejectUser(userId);
        return ResponseEntity.ok().build();
    }
}
