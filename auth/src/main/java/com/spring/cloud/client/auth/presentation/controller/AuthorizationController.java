package com.spring.cloud.client.auth.presentation.controller;


import com.spring.cloud.client.auth.application.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    @GetMapping("/authorize")
    public ResponseEntity<?> authorize(
            @RequestHeader("Authorization") String token,
            @RequestParam("requestedUser") String requestedUser
    ) {
        boolean isAuthorized = authorizationService.authorizeAction(token, requestedUser);
        return ResponseEntity.ok(isAuthorized);
    }
}
