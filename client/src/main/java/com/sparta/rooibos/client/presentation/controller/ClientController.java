package com.sparta.rooibos.client.presentation.controller;

import com.sparta.rooibos.client.application.dto.req.CreateClientRequest;
import com.sparta.rooibos.client.application.dto.req.SearchClientRequest;
import com.sparta.rooibos.client.application.dto.req.UpdateClientRequest;
import com.sparta.rooibos.client.application.dto.req.UpdateHubIdRequest;
import com.sparta.rooibos.client.application.dto.res.CreateClientResponse;
import com.sparta.rooibos.client.application.dto.res.GetClientResponse;
import com.sparta.rooibos.client.application.dto.res.SearchClientResponse;
import com.sparta.rooibos.client.application.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Pageable pageable = PageRequest.of(request.page() - 1, request.size(), Sort.by(Sort.Direction.DESC, request.sort()));
        return ResponseEntity.ok(clientService.getClientList(request, pageable));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<GetClientResponse> getClient(@PathVariable UUID clientId) {
        return ResponseEntity.ok(clientService.getClient(clientId));
    }

    @PostMapping()
    public ResponseEntity<CreateClientResponse> createClient(@RequestBody CreateClientRequest createClientRequest) {
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
    @PatchMapping("/change/hub/{clientId}")
    public ResponseEntity<Boolean> changeUsedHub(@PathVariable UUID clientId, @RequestBody UpdateHubIdRequest request) {
        return ResponseEntity.ok(clientService.changeUsedHub(clientId, request));
    }
}
