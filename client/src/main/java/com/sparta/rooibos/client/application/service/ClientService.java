package com.sparta.rooibos.client.application.service;

import com.sparta.rooibos.client.application.service.dto.req.CreateClientApplicationRequest;
import com.sparta.rooibos.client.application.service.dto.res.CreateClientApplicationResponse;
import com.sparta.rooibos.client.application.service.dto.res.GetClientApplicationResponse;
import com.sparta.rooibos.client.application.service.dto.res.SearchClientApplicationListResponse;
import com.sparta.rooibos.client.application.service.dto.res.SearchClientApplicationResponse;
import com.sparta.rooibos.client.domain.entity.Client;
import com.sparta.rooibos.client.domain.model.ClientType;
import com.sparta.rooibos.client.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public SearchClientApplicationResponse getClientList() {
        //TODO 페이징 적용
        //TODO 쿼리 DSL 적용
        List<Client> clientList = clientRepository.findAll();
        return new SearchClientApplicationResponse(
                clientList.stream()
                        .map(client -> new SearchClientApplicationListResponse(
                                client.getId(),
                                client.getName(),
                                client.getType().name(),
                                client.getManagedHubId(),
                                client.getClientAddress())).toList(), 0L, 0L, 0L);
    }

    //TODO 시간타입 추가
    public GetClientApplicationResponse getClient(UUID clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new IllegalArgumentException("해당 하는 업체가 존재하지 않습니다."));
        return new GetClientApplicationResponse(client.getId().toString(),
                                                client.getName(),
                                                client.getClientAddress(),
                                                client.getType().name(),
                                                LocalDateTime.now(),
                                                LocalDateTime.now());
    }

    public CreateClientApplicationResponse createClient(CreateClientApplicationRequest createClientRequest) {
        //TODO 존재하는 허브인지 확인한다.
        //TODO 동일한 이름의 타입이 같은 업체는 등록을 할 수 없다???
        // 업체 등록
        Client client = clientRepository.save(new Client(
                createClientRequest.name(),
                ClientType.valueOf(createClientRequest.clientType()),
                createClientRequest.managedHubId(),
                createClientRequest.address()));
        return new CreateClientApplicationResponse(client);
    }

    public void updateClient() {

    }

    public void deleteClient() {

    }
}
