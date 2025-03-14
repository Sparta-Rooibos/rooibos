package com.sparta.rooibos.client.presentation.controller;

import com.sparta.rooibos.client.application.service.ClientService;
import com.sparta.rooibos.client.application.service.dto.req.CreateClientApplicationRequest;


import com.sparta.rooibos.client.application.service.dto.req.UpdateClientApplicationRequest;
import com.sparta.rooibos.client.presentation.dto.req.UpdateClientRequest;
import com.sparta.rooibos.client.presentation.dto.res.GetClientResponse;
import com.sparta.rooibos.client.presentation.dto.res.SearchClientResponse;
import com.sparta.rooibos.client.presentation.dto.req.CreateClientRequest;
import com.sparta.rooibos.client.presentation.dto.res.CreateClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client")
public class ClientController {
    private final ClientService clientService;

    //TODO 검색 파라미터 적용
    @GetMapping
    public SearchClientResponse getClientList() {
        return new SearchClientResponse(clientService.getClientList());
    }

    @GetMapping("/{clientId}")
    public GetClientResponse getClient(@PathVariable UUID clientId) {
        return new GetClientResponse(clientService.getClient(clientId));
    }

    @PostMapping()
    public CreateClientResponse createClient(@RequestBody CreateClientRequest createClientRequest) {
        return new CreateClientResponse(clientService.createClient(new CreateClientApplicationRequest(createClientRequest)));

    }

    @PutMapping("/{clientId}")
    public boolean updateClient(@PathVariable UUID clientId, @RequestBody UpdateClientRequest request) {
        return clientService.updateClient(new UpdateClientApplicationRequest(clientId, request));
    }

    @PatchMapping("/{clientId}")
    public boolean deleteClient(@PathVariable UUID clientId) {
        return clientService.deleteClient(clientId);
    }
}
