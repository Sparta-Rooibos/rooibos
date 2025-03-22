package com.sparta.rooibos.client.application.service;

import com.sparta.rooibos.client.application.dto.request.CreateClientManagerRequest;
import com.sparta.rooibos.client.application.dto.response.CreateClientManagerResponse;
import com.sparta.rooibos.client.application.dto.response.GetClientManagerResponse;
import com.sparta.rooibos.client.application.exception.BusinessClientException;
import com.sparta.rooibos.client.application.exception.ClientErrorCode;
import com.sparta.rooibos.client.domain.entity.ClientManager;
import com.sparta.rooibos.client.domain.repository.ClientManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientManagerService {
    private final ClientManagerRepository repository;

    public CreateClientManagerResponse createClientManager(CreateClientManagerRequest request) {
        List<ClientManager> managers = repository.findAllByClientId(request.clientId()).stream().toList();
        if(!managers.isEmpty()){
            throw new BusinessClientException(ClientErrorCode.NOT_EXITS_CLIENT_MANAGER);
        }
        return CreateClientManagerResponse.create(repository.save(request.toEntity()));
    }

    public GetClientManagerResponse getClientManager(UUID clientId) {
        ClientManager clientManager = repository.findAllByClientId(clientId).get(0);
        return GetClientManagerResponse.get(clientManager);
    }

    public void deleteClientManager(UUID clientId) {
        repository.deleteByClientId(clientId);
    }

    public String getClientIdByUserId(String email) {

        return repository.getClientIdByUserId(email);
    }
}
