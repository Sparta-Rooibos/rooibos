package com.sparta.rooibos.user.presentation.controller;

import com.sparta.rooibos.user.application.dto.request.UserRequest;
import com.sparta.rooibos.user.application.dto.request.UserUpdateRequest;
import com.sparta.rooibos.user.application.dto.response.CachedUserResponse;
import com.sparta.rooibos.user.application.dto.response.UserResponse;
import com.sparta.rooibos.user.application.service.port.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @GetMapping()
    public ResponseEntity<UserResponse> getUser() {
        return ResponseEntity.ok(userService.getUser());
    }

    @PutMapping()
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateUser(request));
    }

    @PatchMapping("/delete")
    public ResponseEntity<Void> deleteAccount() {
        userService.deleteUser();
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/report")
    public ResponseEntity<Void> reportUser() {
        userService.reportUser();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/internal/{email}")
    public ResponseEntity<CachedUserResponse> getUserForAuth(@PathVariable String email) {
        log.info("[UserController] 내부 유저 조회 진입 - email: " + email);
        return ResponseEntity.ok(userService.getUserForAuth(email));
    }
}