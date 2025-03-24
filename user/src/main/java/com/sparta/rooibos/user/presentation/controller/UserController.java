package com.sparta.rooibos.user.presentation.controller;

import com.sparta.rooibos.user.application.dto.request.UserRequest;
import com.sparta.rooibos.user.application.dto.response.UserResponse;
import com.sparta.rooibos.user.application.service.port.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserResponse> signup(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @GetMapping()
    public ResponseEntity<UserResponse> getUser() {
        return ResponseEntity.ok(userService.getUser());
    }

    @PutMapping()
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(userService.updateUser(userRequest));
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
}