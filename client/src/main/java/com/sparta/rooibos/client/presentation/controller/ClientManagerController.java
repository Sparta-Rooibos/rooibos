package com.sparta.rooibos.client.presentation.controller;

import com.sparta.rooibos.client.application.annotation.RoleCheck;
import com.sparta.rooibos.client.application.dto.request.CreateClientManagerRequest;
import com.sparta.rooibos.client.application.dto.response.CreateClientManagerResponse;
import com.sparta.rooibos.client.application.dto.response.GetClientManagerResponse;
import com.sparta.rooibos.client.application.service.ClientManagerService;
import com.sparta.rooibos.client.application.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client/manager")
public class ClientManagerController {
    private final ClientManagerService service;

    //생성
    @PostMapping
    @RoleCheck({Role.MASTER, Role.CLIENT})
    public ResponseEntity<CreateClientManagerResponse> createClientManager(
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @RequestBody CreateClientManagerRequest request) {
        return ResponseEntity.ok(service.createClientManager(request));
    }

    //조회
    @GetMapping("/{clientId}")
    @RoleCheck({Role.MASTER, Role.CLIENT})
    public ResponseEntity<GetClientManagerResponse> getClientManager(
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable UUID clientId) {
        return ResponseEntity.ok(service.getClientManager(clientId));
    }

    // 삭제
    @DeleteMapping("/{clientId}")
    @RoleCheck({Role.MASTER, Role.CLIENT})
    public ResponseEntity<Void> deleteClientManager(
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable UUID clientId) {
        service.deleteClientManager(clientId);
        return ResponseEntity.ok().build();
    }
}
