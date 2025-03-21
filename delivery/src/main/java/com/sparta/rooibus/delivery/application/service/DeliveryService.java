package com.sparta.rooibus.delivery.application.service;

import com.sparta.rooibus.delivery.application.dto.request.CreateDeliveryRequest;
import com.sparta.rooibus.delivery.application.dto.request.SearchDeliveryRequestDTO;
import com.sparta.rooibus.delivery.application.dto.request.UpdateDeliveryRequest;
import com.sparta.rooibus.delivery.application.dto.request.feign.client.GetClientManagerRequest;
import com.sparta.rooibus.delivery.application.dto.request.feign.client.GetClientRequest;
import com.sparta.rooibus.delivery.application.dto.request.feign.deliverAgent.GetDeliverRequest;
import com.sparta.rooibus.delivery.application.dto.request.feign.user.GetUserRequest;
import com.sparta.rooibus.delivery.application.dto.response.CreateDeliveryResponse;
import com.sparta.rooibus.delivery.application.dto.response.GetDeliveryResponse;
import com.sparta.rooibus.delivery.application.dto.response.SearchDeliveryResponse;
import com.sparta.rooibus.delivery.application.dto.response.UpdateDeliveryResponse;
import com.sparta.rooibus.delivery.application.dto.response.feign.client.GetClientResponse;
import com.sparta.rooibus.delivery.application.service.feign.ClientService;
import com.sparta.rooibus.delivery.application.service.feign.DeliveryAgentService;
import com.sparta.rooibus.delivery.application.service.feign.UserService;
import com.sparta.rooibus.delivery.domain.entity.Delivery;
import com.sparta.rooibus.delivery.domain.repository.DeliveryRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final ClientService clientService;
    private final UserService userService;
    private final DeliveryAgentService deliveryAgentService;

    @Transactional
    public CreateDeliveryResponse createDelivery(CreateDeliveryRequest request) {
        UUID departure = clientService.getClient(GetClientRequest.from(request.requestClientId())).manageHub().id();

        GetClientResponse getClientResponse = clientService.getClient(GetClientRequest.from(request.requestClientId()));
        UUID arrival = getClientResponse.manageHub().id();
        String address = getClientResponse.address();

        UUID recipient = clientService.getClientManager(GetClientManagerRequest.from(request.requestClientId())).clientManagerId();

        String slackAccount = userService.getUser(GetUserRequest.from(recipient)).slackAccount();

        UUID clientDeliverId = deliveryAgentService.getDeliver(GetDeliverRequest.from(request.requestClientId())).deliverId();

        Delivery delivery = Delivery.of(
            request.orderId(),
            departure,
            arrival,
            address,
            recipient,
            slackAccount,
            clientDeliverId
        );
        deliveryRepository.save(delivery);

        return CreateDeliveryResponse.from(delivery);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "deliveryCache", key = "#deliveryId")
    public GetDeliveryResponse getDelivery(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(
            ()-> new EntityNotFoundException("찾으시는 데이터가 올바르지 않습니다.")
        );
        return GetDeliveryResponse.from(delivery);
    }

    @Transactional
    @CachePut(value = "deliveryCache", key = "#request.deliveryId()")
    public UpdateDeliveryResponse updateDelivery(UpdateDeliveryRequest request) {
        Delivery delivery = deliveryRepository.findById(request.deliveryId()).orElseThrow(
            ()-> new EntityNotFoundException("찾으시는 데이터가 올바르지 않습니다.")
        );
        delivery.update(request);
        return UpdateDeliveryResponse.from(delivery);
    }

    @Transactional
    @CacheEvict(value = "deliveryCache", key = "#deliveryId")
    public UUID deleteDelivery(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(
            ()-> new EntityNotFoundException("찾으시는 데이터가 올바르지 않습니다.")
        );
        delivery.validateDeletable();
        delivery.delete();
        return delivery.getId();
    }

    @Cacheable(value = "searchDeliveryCache", key = "#request.page() + '-' + #request.size()")
    public Page<SearchDeliveryResponse> searchOrders(SearchDeliveryRequestDTO request) {
        PageRequest pageRequest = PageRequest.of(request.page(), request.size());
        Page<Delivery> deliveryPage = deliveryRepository.searchOrders(request, pageRequest);

        List<SearchDeliveryResponse> deliverySearchList = deliveryPage.getContent()
            .stream()
            .map(SearchDeliveryResponse::new)
            .toList();

        return new PageImpl<>(deliverySearchList,pageRequest,deliveryPage.getTotalElements());
    }
}
