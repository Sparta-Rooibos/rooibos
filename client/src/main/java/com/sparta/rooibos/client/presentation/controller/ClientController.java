package com.sparta.rooibos.client.presentation.controller;

import com.sparta.rooibos.client.application.service.ClientService;
import com.sparta.rooibos.client.application.service.dto.req.CreateClientApplicationRequest;


import com.sparta.rooibos.client.presentation.dto.res.SearchClientResponse;
import com.sparta.rooibos.client.presentation.dto.req.CreateClientRequest;
import com.sparta.rooibos.client.presentation.dto.res.CreateClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
