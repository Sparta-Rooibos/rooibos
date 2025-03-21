package com.sparta.rooibos.client.presentation.controller;

import com.sparta.rooibos.client.application.dto.request.CreateClientManagerRequest;
import com.sparta.rooibos.client.application.dto.response.CreateClientManagerResponse;
import com.sparta.rooibos.client.application.dto.response.GetClientManagerResponse;
import com.sparta.rooibos.client.application.service.ClientManagerService;
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
    public ResponseEntity<CreateClientManagerResponse> createClientManager(@RequestBody CreateClientManagerRequest request) {
        return ResponseEntity.ok(service.createClientManager(request));
    }

    //조회
    @GetMapping("/{clientId}")
    public ResponseEntity<GetClientManagerResponse> getClientManager(@PathVariable UUID clientId) {
        return ResponseEntity.ok(service.getClientManager(clientId));
    }

    // 삭제
    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClientManager(@PathVariable UUID clientId) {
        service.deleteClientManager(clientId);
        return ResponseEntity.ok().build();
    }
}
