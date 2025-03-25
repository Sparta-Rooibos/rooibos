package com.sparta.rooibos.client.presentation.controller;


import com.sparta.rooibos.client.application.annotation.RoleCheck;
import com.sparta.rooibos.client.application.dto.request.CreateClientRequest;
import com.sparta.rooibos.client.application.dto.request.SearchClientRequest;
import com.sparta.rooibos.client.application.dto.request.UpdateClientRequest;
import com.sparta.rooibos.client.application.dto.request.UpdateHubIdRequest;
import com.sparta.rooibos.client.application.dto.response.CreateClientResponse;
import com.sparta.rooibos.client.application.dto.response.GetClientResponse;
import com.sparta.rooibos.client.application.dto.response.SearchClientResponse;
import com.sparta.rooibos.client.application.service.ClientManagerService;
import com.sparta.rooibos.client.application.service.ClientService;
import com.sparta.rooibos.client.application.type.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client")
public class ClientController {
    private final ClientService clientService;

    @GetMapping
    @RoleCheck({Role.MASTER, Role.HUB, Role.DELIVERY, Role.CLIENT})
    public ResponseEntity<SearchClientResponse> getClientList(
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @ModelAttribute SearchClientRequest request) {
        return ResponseEntity.ok(clientService.searchClient(request));
    }

    @GetMapping("/{clientId}")
    @RoleCheck({Role.MASTER, Role.HUB, Role.DELIVERY, Role.CLIENT})
    public ResponseEntity<GetClientResponse> getClient(
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable UUID clientId) {
        return ResponseEntity.ok(clientService.getClient(clientId));
    }

    @PostMapping()
    @RoleCheck({Role.MASTER, Role.HUB})
    public ResponseEntity<CreateClientResponse> createClient(
            @RequestHeader(name = "X-User-Email") String email,
            @RequestHeader(name = "X-User-Name") String username,
            @RequestHeader(name = "X-User-Role") String role,
            @RequestBody @Valid CreateClientRequest createClientRequest) {
        return ResponseEntity.ok(clientService.createClient(email, createClientRequest));

    }

    @PutMapping("/{clientId}")
    @RoleCheck({Role.MASTER, Role.HUB, Role.CLIENT})
    public ResponseEntity<Boolean> updateClient(
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable UUID clientId, @RequestBody UpdateClientRequest request) {
        return ResponseEntity.ok(clientService.updateClient(email, role, clientId, request));
    }

    @PatchMapping("/{clientId}")
    @RoleCheck({Role.MASTER, Role.HUB})
    public ResponseEntity<Boolean> deleteClient(
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable UUID clientId) {
        return ResponseEntity.ok(clientService.deleteClient(email, clientId));
    }

    //사용 허브 변경
    @Deprecated
    @RoleCheck(Role.MASTER)
    @PatchMapping("/change/hub/{clientId}")
    public ResponseEntity<Boolean> changeUsedHub(
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable UUID clientId, @RequestBody UpdateHubIdRequest request) {
        return ResponseEntity.ok(clientService.changeUsedHub(clientId, request));
    }
}
