package com.sparta.rooibos.client.application.service;


import com.sparta.rooibos.client.application.dto.request.CreateClientRequest;
import com.sparta.rooibos.client.application.dto.request.SearchClientRequest;
import com.sparta.rooibos.client.application.dto.request.UpdateClientRequest;
import com.sparta.rooibos.client.application.dto.request.UpdateHubIdRequest;
import com.sparta.rooibos.client.application.dto.response.CreateClientResponse;
import com.sparta.rooibos.client.application.dto.response.GetClientResponse;
import com.sparta.rooibos.client.application.dto.response.SearchClientListResponse;
import com.sparta.rooibos.client.application.dto.response.SearchClientResponse;
import com.sparta.rooibos.client.application.exception.BusinessClientException;
import com.sparta.rooibos.client.application.exception.ClientErrorCode;
import com.sparta.rooibos.client.application.feigin.service.HubService;
import com.sparta.rooibos.client.application.feigin.service.dto.ManageHub;
import com.sparta.rooibos.client.domain.entity.Client;
import com.sparta.rooibos.client.domain.entity.ClientType;
import com.sparta.rooibos.client.domain.fegin.hub.model.Hub;
import com.sparta.rooibos.client.domain.model.Pagination;
import com.sparta.rooibos.client.domain.repository.ClientRepository;
import com.sparta.rooibos.client.domain.repository.QueryClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final QueryClientRepository queryClientRepository;
    private final HubService hubService;

    public SearchClientResponse searchClient(SearchClientRequest condition) {
        Pagination<Client> clients = queryClientRepository.searchClient(condition.toCriteria());
        return new SearchClientResponse(
                clients.getContent().stream()
                        .map(client -> new SearchClientListResponse(
                                client.getId(),
                                client.getName(),
                                client.getType().name(),
                                client.getManagedHubId(),
                                client.getClientAddress(),
                                client.getDeleteBy() != null)).toList(),
                clients.getTotal(),
                clients.getPage(),
                clients.getSize());
    }

    public GetClientResponse getClient(UUID clientId) {
        Client client = clientRepository.findByIdAndDeleteByIsNull(clientId).orElseThrow(() -> new BusinessClientException(ClientErrorCode.NOT_EXITS_CLIENT));
        Hub hub = hubService.getHub(client.getManagedHubId());
        return new GetClientResponse(client.getId().toString(),
                client.getName(),
                client.getClientAddress(),
                client.getType().name(),
                new ManageHub(hub) ,
                client.getCreateAt(),
                client.getUpdateAt());
    }

    public CreateClientResponse createClient(CreateClientRequest createClientRequest) {
        if (clientRepository.findByNameAndDeleteByIsNull(createClientRequest.name()).isPresent()) {
            throw new BusinessClientException(ClientErrorCode.NOT_FOUND_CLIENT);
        }

        //TODO 계정 아이디를 등록해주면 된다.
        //업체 등록
        Client client = clientRepository.save(new Client(
                createClientRequest.name(),
                ClientType.valueOf(createClientRequest.clientType()),
                createClientRequest.managedHubId(),
                createClientRequest.address(),
                "계정아이디"));
        return new CreateClientResponse(client);
    }

    @Transactional
    public boolean updateClient(UUID clientId, UpdateClientRequest request) {
        if (clientRepository.findByNameAndDeleteByIsNull(request.name())
                .filter(client -> !client.getId().equals(clientId)).isPresent()) {
            throw new BusinessClientException(ClientErrorCode.NOT_FOUND_CLIENT);
        }
        Client client = clientRepository.findByIdAndDeleteByIsNull(clientId).orElseThrow(() -> new BusinessClientException(ClientErrorCode.NOT_EXITS_CLIENT));
        client.update(request.name(), request.address(), "계정아이디");
        return true;
    }

    @Transactional
    public boolean deleteClient(UUID id) {
        Client client = clientRepository.findByIdAndDeleteByIsNull(id).orElseThrow(() -> new BusinessClientException(ClientErrorCode.NOT_EXITS_CLIENT));
        //TODO 삭제시 계정 ID를 가져온다.
        return client.delete("계정아이디");
    }

    @Transactional
    public boolean changeUsedHub(UUID clientId, UpdateHubIdRequest request) {
        Client client = clientRepository.findByIdAndDeleteByIsNull(clientId).orElseThrow(() -> new BusinessClientException(ClientErrorCode.NOT_EXITS_CLIENT));
        return client.changeUsedHub(request.hubId());
    }
}
