package com.sparta.rooibos.client.application.service;

import com.sparta.rooibos.client.application.service.dto.req.CreateClientApplicationRequest;
import com.sparta.rooibos.client.application.service.dto.res.CreateClientApplicationResponse;
import com.sparta.rooibos.client.domain.entity.Client;
import com.sparta.rooibos.client.domain.model.ClientType;
import com.sparta.rooibos.client.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    public void getClientList() {

    }

    public void getClient() {

    }

    public CreateClientApplicationResponse createClient(CreateClientApplicationRequest createClientRequest) {
        //TODO 존재하는 허브인지 확인한다.
        //TODO 동일한 이름의 타입이 같은 업체는 등록을 할 수 없다???
        // 업체 등록
        Client client = clientRepository.save(new Client(createClientRequest.name(),
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
