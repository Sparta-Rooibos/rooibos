package com.sparta.rooibos.client.application.service;


import com.sparta.rooibos.client.application.dto.condition.SearchClientApplicationCondition;
import com.sparta.rooibos.client.application.dto.req.CreateClientApplicationRequest;
import com.sparta.rooibos.client.application.dto.req.UpdateClientApplicationRequest;
import com.sparta.rooibos.client.application.dto.req.UpdateHubIdApplicationRequest;
import com.sparta.rooibos.client.application.dto.res.CreateClientApplicationResponse;
import com.sparta.rooibos.client.application.dto.res.GetClientApplicationResponse;
import com.sparta.rooibos.client.application.dto.res.SearchClientApplicationListResponse;
import com.sparta.rooibos.client.application.dto.res.SearchClientApplicationResponse;
import com.sparta.rooibos.client.domain.entity.Client;
import com.sparta.rooibos.client.domain.model.ClientType;
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

    public SearchClientApplicationResponse getClientList(SearchClientApplicationCondition condition) {
        Page<Client> clients = queryClientRepository.getClientList(condition.getPageable(),
                condition.getName(),
                condition.getAddress(),
                condition.getType());
        return new SearchClientApplicationResponse(
                clients.getContent().stream()
                        .map(client -> new SearchClientApplicationListResponse(
                                client.getId(),
                                client.getName(),
                                client.getType().name(),
                                client.getManagedHubId(),
                                client.getClientAddress())).toList(),
                clients.getTotalElements(),
                clients.getNumber() + 1,
                clients.getSize());
    }

    public GetClientApplicationResponse getClient(UUID clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new IllegalArgumentException("해당 하는 업체가 존재하지 않습니다."));
        return new GetClientApplicationResponse(client.getId().toString(),
                client.getName(),
                client.getClientAddress(),
                client.getType().name(),
                client.getManagedHubId(),
                client.getCreateAt(),
                client.getUpdateAt());
    }

    public CreateClientApplicationResponse createClient(CreateClientApplicationRequest createClientRequest) {
        //TODO 존재하는 허브인지 확인한다.
        //TODO 동일한 이름의 타입이 같은 업체는 등록을 할 수 없다???
        //TODO 계정 아이디를 등록해주면 된다.
        // 업체 등록
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
        Client client = clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 하는 업체가 존재하지 않습니다."));
        client.update(request.name(), request.address(), "계정아이디");
        return true;
    }

    @Transactional
    public boolean deleteClient(UUID id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 하는 업체가 존재하지 않습니다."));
        //TODO 삭제시 계정 ID를 가져온다.
        return client.delete("계정아이디");
    }

    @Transactional
    public boolean changeUsedHub(UpdateHubIdApplicationRequest request) {
        UUID id = request.clientId();
        Client client = clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 하는 업체가 존재하지 않습니다."));
        return client.changeUsedHub(request.hubId());
    }
}
