package com.sparta.rooibos.client.presentation.controller;

import com.sparta.rooibos.client.application.service.ClientService;
import com.sparta.rooibos.client.application.service.dto.req.CreateClientApplicationRequest;

import com.sparta.rooibos.client.presentation.dto.req.CreateClientRequest;
import com.sparta.rooibos.client.presentation.dto.res.CreateClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client")
public class ClientController {
    private final ClientService clientService;
    @GetMapping
    public void getClientList() {
        clientService.getClientList();
    }
    @GetMapping("/{clientId}")
    public void getClient() {
        clientService.getClient();
    }
    @PostMapping()
    public CreateClientResponse createClient(@RequestBody CreateClientRequest createClientRequest) {
        return new CreateClientResponse(clientService.createClient(new CreateClientApplicationRequest(createClientRequest)));

    }
    @PutMapping("/{clientId}")
    public void updateClient() {
        clientService.updateClient();
    }
    @PatchMapping("/{clientId}")
    public void deleteClient() {
        clientService.deleteClient();
    }
}
