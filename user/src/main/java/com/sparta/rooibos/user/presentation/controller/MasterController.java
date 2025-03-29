package com.sparta.rooibos.user.presentation.controller;

import com.sparta.rooibos.user.application.dto.request.UserRequest;
import com.sparta.rooibos.user.application.dto.request.UserSearchRequest;
import com.sparta.rooibos.user.application.dto.request.UserUpdateRequest;
import com.sparta.rooibos.user.application.dto.response.UserListResponse;
import com.sparta.rooibos.user.application.dto.response.UserResponse;
import com.sparta.rooibos.user.application.service.port.MasterService;
import com.sparta.rooibos.user.infrastructure.aop.MasterOnlyCheck;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user/master")
@RequiredArgsConstructor
public class MasterController {

    private final MasterService masterService;

    @MasterOnlyCheck
    @PostMapping
    public ResponseEntity<UserResponse> createUserByMaster(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(masterService.createUserByMaster(userRequest));
    }

    @MasterOnlyCheck
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserByMaster(@PathVariable UUID userId) {
        return ResponseEntity.ok(masterService.getUserByMaster(userId));
    }

    @MasterOnlyCheck
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUserByMaster(@PathVariable UUID userId, @RequestBody @Valid UserUpdateRequest request) {
        return ResponseEntity.ok(masterService.updateUserByMaster(userId, request));
    }

    @MasterOnlyCheck
    @PatchMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUserByMaster(@PathVariable UUID userId) {
        masterService.deleteUserByMaster(userId);
        return ResponseEntity.ok().build();
    }

    @MasterOnlyCheck
    @PatchMapping("/report/{userId}")
    public ResponseEntity<Void> reportUserByMaster(@PathVariable UUID userId) {
        masterService.reportUserByMaster(userId);
        return ResponseEntity.ok().build();
    }

    @MasterOnlyCheck
    @GetMapping
    public ResponseEntity<UserListResponse> searchUsersByMaster(@ModelAttribute UserSearchRequest request) {
        return ResponseEntity.ok(masterService.searchUsersByMaster(request));
    }
}
