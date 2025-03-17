package com.spring.cloud.client.user.presentation;

import com.spring.cloud.client.user.application.dto.UserRequest;
import com.spring.cloud.client.user.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

//    @RoleCheck("ALL")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

//    @RoleCheck("MASTER")
    @PostMapping("/master")
    public ResponseEntity<?> createUserByMaster(@RequestBody @Valid UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

//    @RoleCheck("ALL")
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUser(@PathVariable UUID userId) {
        return userService.getUser(userId);
    }

//    @RoleCheck("ALL")
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable UUID userId, @RequestBody @Valid UserRequest userRequest) {
        return userService.updateUser(userId, userRequest);
    }

//    @RoleCheck("ALL")
    @PatchMapping("/{userId}")
    public ResponseEntity<?> deleteAccount(@PathVariable UUID userId) {
        return userService.deleteUser(userId);
    }

//    @RoleCheck("MASTER")
    @PatchMapping("/{userId}/master")
    public ResponseEntity<?> deleteUserByMaster(@PathVariable UUID userId) {
        return userService.deleteUser(userId);
    }

//    @RoleCheck("ALL")
    @PatchMapping("/report/{userId}")
    public ResponseEntity<?> reportUser(@PathVariable UUID userId) {
        return userService.reportUser(userId);
    }
}