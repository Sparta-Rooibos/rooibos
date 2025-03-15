package com.sparta.rooibos.client.presentation.controller;

import com.sparta.rooibos.client.application.dto.condition.SearchClientApplicationCondition;
import com.sparta.rooibos.client.application.dto.req.CreateClientApplicationRequest;
import com.sparta.rooibos.client.application.service.ClientService;


import com.sparta.rooibos.client.application.dto.req.UpdateClientApplicationRequest;
import com.sparta.rooibos.client.presentation.dto.req.UpdateClientRequest;
import com.sparta.rooibos.client.presentation.dto.res.GetClientResponse;
import com.sparta.rooibos.client.presentation.dto.res.SearchClientResponse;
import com.sparta.rooibos.client.presentation.dto.req.CreateClientRequest;
import com.sparta.rooibos.client.presentation.dto.res.CreateClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client")
public class ClientController {
    private final ClientService clientService;

    @GetMapping
    public SearchClientResponse getClientList(@RequestParam(required = false) String name,
                                              @RequestParam(required = false) String address,
                                              @RequestParam(required = false) String type,
                                              @RequestParam(defaultValue = "createdAt") String sort,
                                              @RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, sort));
        return new SearchClientResponse(clientService.getClientList(new SearchClientApplicationCondition(name, address, type, pageable)));
    }

    @GetMapping("/{clientId}")
    public GetClientResponse getClient(@PathVariable UUID clientId) {
        return new GetClientResponse(clientService.getClient(clientId));
    }

    @PostMapping()
    public CreateClientResponse createClient(@RequestBody CreateClientRequest createClientRequest) {
        return new CreateClientResponse(clientService.createClient(createClientRequest.toApplication()));

    }

    @PutMapping("/{clientId}")
    public boolean updateClient(@PathVariable UUID clientId, @RequestBody UpdateClientRequest request) {
        return clientService.updateClient(request.toApplication(clientId));
    }

    @PatchMapping("/{clientId}")
    public boolean deleteClient(@PathVariable UUID clientId) {
        return clientService.deleteClient(clientId);
    }
    //사용 허브 변경
}
