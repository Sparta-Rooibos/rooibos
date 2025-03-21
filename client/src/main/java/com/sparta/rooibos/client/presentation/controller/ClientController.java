package com.sparta.rooibos.client.presentation.controller;


import com.sparta.rooibos.client.application.dto.request.CreateClientRequest;
import com.sparta.rooibos.client.application.dto.request.SearchClientRequest;
import com.sparta.rooibos.client.application.dto.request.UpdateClientRequest;
import com.sparta.rooibos.client.application.dto.request.UpdateHubIdRequest;
import com.sparta.rooibos.client.application.dto.response.CreateClientResponse;
import com.sparta.rooibos.client.application.dto.response.GetClientResponse;
import com.sparta.rooibos.client.application.dto.response.SearchClientResponse;
import com.sparta.rooibos.client.application.service.ClientService;
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
    public ResponseEntity<SearchClientResponse> getClientList(
            @ModelAttribute SearchClientRequest request) {
       return ResponseEntity.ok(clientService.searchClient(request));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<GetClientResponse> getClient(@PathVariable UUID clientId) {
        return ResponseEntity.ok(clientService.getClient(clientId));
    }

    @PostMapping()
    public ResponseEntity<CreateClientResponse> createClient(@RequestBody @Valid CreateClientRequest createClientRequest) {
        return ResponseEntity.ok(clientService.createClient(createClientRequest));

    }

    @PutMapping("/{clientId}")
    public ResponseEntity<Boolean> updateClient(@PathVariable UUID clientId, @RequestBody UpdateClientRequest request) {
        return ResponseEntity.ok(clientService.updateClient(clientId, request));
    }

    @PatchMapping("/{clientId}")
    public ResponseEntity<Boolean> deleteClient(@PathVariable UUID clientId) {
        return ResponseEntity.ok(clientService.deleteClient(clientId));
    }

    //사용 허브 변경
    @Deprecated
    @PatchMapping("/change/hub/{clientId}")
    public ResponseEntity<Boolean> changeUsedHub(@PathVariable UUID clientId, @RequestBody UpdateHubIdRequest request) {
        return ResponseEntity.ok(clientService.changeUsedHub(clientId, request));
    }
}
