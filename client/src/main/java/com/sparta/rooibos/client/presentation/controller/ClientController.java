package com.sparta.rooibos.client.presentation.controller;

import com.sparta.rooibos.client.application.service.ClientService;
import com.sparta.rooibos.client.presentation.dto.req.CreateClientRequest;
import com.sparta.rooibos.client.presentation.dto.req.SearchClientRequest;
import com.sparta.rooibos.client.presentation.dto.req.UpdateClientRequest;
import com.sparta.rooibos.client.presentation.dto.req.UpdateHubIdRequest;
import com.sparta.rooibos.client.presentation.dto.res.CreateClientResponse;
import com.sparta.rooibos.client.presentation.dto.res.GetClientResponse;
import com.sparta.rooibos.client.presentation.dto.res.SearchClientResponse;
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
        return ResponseEntity.ok(new SearchClientResponse(clientService.getClientList(request.toApplication(pageable))));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<GetClientResponse> getClient(@PathVariable UUID clientId) {
        return ResponseEntity.ok(new GetClientResponse(clientService.getClient(clientId)));
    }

    @PostMapping()
    public ResponseEntity<CreateClientResponse> createClient(@RequestBody CreateClientRequest createClientRequest) {
        return ResponseEntity.ok(new CreateClientResponse(clientService.createClient(createClientRequest.toApplication())));

    }

    @PutMapping("/{clientId}")
    public ResponseEntity<Boolean> updateClient(@PathVariable UUID clientId, @RequestBody UpdateClientRequest request) {
        return ResponseEntity.ok(clientService.updateClient(request.toApplication(clientId)));
    }

    @PatchMapping("/{clientId}")
    public ResponseEntity<Boolean> deleteClient(@PathVariable UUID clientId) {
        return ResponseEntity.ok(clientService.deleteClient(clientId));
    }

    //사용 허브 변경
    @PatchMapping("/change/hub/{clientId}")
    public ResponseEntity<Boolean> changeUsedHub(@PathVariable UUID clientId, @RequestBody UpdateHubIdRequest request) {
        return ResponseEntity.ok(clientService.changeUsedHub(request.toApplication(clientId)));
    }
}
