package com.sparta.rooibos.client.application.service;


import com.sparta.rooibos.client.application.dto.req.CreateClientApplicationRequest;
import com.sparta.rooibos.client.application.dto.req.SearchClientApplicationRequest;
import com.sparta.rooibos.client.application.dto.req.UpdateClientApplicationRequest;
import com.sparta.rooibos.client.application.dto.req.UpdateHubIdApplicationRequest;
import com.sparta.rooibos.client.application.dto.res.CreateClientApplicationResponse;
import com.sparta.rooibos.client.application.dto.res.GetClientApplicationResponse;
import com.sparta.rooibos.client.application.dto.res.SearchClientApplicationListResponse;
import com.sparta.rooibos.client.application.dto.res.SearchClientApplicationResponse;
import com.sparta.rooibos.client.application.exception.BusinessClientException;
import com.sparta.rooibos.client.application.exception.ClientErrorCode;
import com.sparta.rooibos.client.domain.entity.Client;
import com.sparta.rooibos.client.domain.entity.ClientType;
import com.sparta.rooibos.client.domain.repository.ClientRepository;
import com.sparta.rooibos.client.domain.repository.QueryClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final QueryClientRepository queryClientRepository;

    public SearchClientApplicationResponse getClientList(SearchClientApplicationRequest condition) {
        Page<Client> clients = queryClientRepository.getClientList(condition.pageable(),
                condition.name(),
                condition.address(),
                condition.type(),
                condition.isDeleted());
        return new SearchClientApplicationResponse(
                clients.getContent().stream()
                        .map(client -> new SearchClientApplicationListResponse(
                                client.getId(),
                                client.getName(),
                                client.getType().name(),
                                client.getManagedHubId(),
                                client.getClientAddress(),
                                client.getDeleteBy() != null)).toList(),
                clients.getTotalElements(),
                clients.getNumber() + 1,
                clients.getSize());
    }

    public GetClientApplicationResponse getClient(UUID clientId) {
        Client client = clientRepository.findByIdAndDeleteByIsNull(clientId).orElseThrow(() -> new IllegalArgumentException("해당 하는 업체가 존재하지 않습니다."));
        return new GetClientApplicationResponse(client.getId().toString(),
                client.getName(),
                client.getClientAddress(),
                client.getType().name(),
                client.getManagedHubId(),
                client.getCreateAt(),
                client.getUpdateAt());
    }

    public CreateClientApplicationResponse createClient(CreateClientApplicationRequest createClientRequest) {
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
        return new CreateClientApplicationResponse(client);
    }

    @Transactional
    public boolean updateClient(UpdateClientApplicationRequest request) {
        UUID id = request.clientId();
        if (clientRepository.findByNameAndDeleteByIsNull(request.name())
                .filter(client -> !client.getId().equals(request.clientId())).isPresent()) {
            throw new BusinessClientException(ClientErrorCode.NOT_FOUND_CLIENT);
        }
        Client client = clientRepository.findByIdAndDeleteByIsNull(id).orElseThrow(() -> new BusinessClientException(ClientErrorCode.NOT_EXITS_CLIENT));
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
    public boolean changeUsedHub(UpdateHubIdApplicationRequest request) {
        UUID id = request.clientId();
        Client client = clientRepository.findByIdAndDeleteByIsNull(id).orElseThrow(() -> new BusinessClientException(ClientErrorCode.NOT_EXITS_CLIENT));
        return client.changeUsedHub(request.hubId());
    }
}
