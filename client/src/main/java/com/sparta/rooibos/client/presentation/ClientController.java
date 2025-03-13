package com.sparta.rooibos.client.presentation;

import com.sparta.rooibos.client.application.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
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
    @PostMapping("/{clientId}")
    public void createClient() {
        clientService.createClient();
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
