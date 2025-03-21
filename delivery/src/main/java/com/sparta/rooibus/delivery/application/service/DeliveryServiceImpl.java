package com.sparta.rooibus.delivery.application.service;

import com.sparta.rooibus.delivery.application.aop.UserContextRequestBean;
import com.sparta.rooibus.delivery.application.dto.request.CreateDeliveryRequest;
import com.sparta.rooibus.delivery.application.dto.request.SearchRequest;
import com.sparta.rooibus.delivery.application.dto.request.UpdateDeliveryRequest;
import com.sparta.rooibus.delivery.application.dto.request.feign.client.GetClientManagerRequest;
import com.sparta.rooibus.delivery.application.dto.request.feign.client.GetClientRequest;
import com.sparta.rooibus.delivery.application.dto.request.feign.deliverAgent.GetDeliverRequest;
import com.sparta.rooibus.delivery.application.dto.request.feign.deliverAgent.GetHubDeliverRequest;
import com.sparta.rooibus.delivery.application.dto.request.feign.route.GetRouteRequest;
import com.sparta.rooibus.delivery.application.dto.request.feign.user.GetUserRequest;
import com.sparta.rooibus.delivery.application.dto.response.CreateDeliveryResponse;
import com.sparta.rooibus.delivery.application.dto.response.GetDeliveryResponse;
import com.sparta.rooibus.delivery.application.dto.response.SearchDeliveryResponse;
import com.sparta.rooibus.delivery.application.dto.response.UpdateDeliveryResponse;
import com.sparta.rooibus.delivery.application.dto.response.feign.client.GetClientResponse;
import com.sparta.rooibus.delivery.application.dto.response.feign.deliverAgent.GetHubDeliverResponse;
import com.sparta.rooibus.delivery.application.dto.response.feign.route.GetRouteResponse;
import com.sparta.rooibus.delivery.application.service.feign.ClientService;
import com.sparta.rooibus.delivery.application.service.feign.DeliveryAgentService;
import com.sparta.rooibus.delivery.application.service.feign.RouteService;
import com.sparta.rooibus.delivery.application.service.feign.UserService;
import com.sparta.rooibus.delivery.domain.entity.Delivery;
import com.sparta.rooibus.delivery.domain.entity.DeliveryLog;
import com.sparta.rooibus.delivery.domain.model.Pagination;
import com.sparta.rooibus.delivery.domain.repository.DeliveryLogRepository;
import com.sparta.rooibus.delivery.domain.repository.DeliveryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.BadRequestException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl {
    private final DeliveryRepository deliveryRepository;
    private final ClientService clientService;
    private final UserService userService;
    private final DeliveryAgentService deliveryAgentService;
    private final RouteService routeService;
    private final DeliveryLogRepository deliveryLogRepository;
    private final UserContextRequestBean userContext;

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

        GetRouteResponse routeResponse = routeService.getRoute(GetRouteRequest.of(departure,arrival));
        String expectedDistance = routeResponse.expected_distance();
        String expectedTime = routeResponse.expected_time();
        String sequence = routeResponse.routeList().toString();

        GetHubDeliverResponse deliverResponse = deliveryAgentService.getHubDeliver(
            GetHubDeliverRequest.from(departure));
        UUID deliverId = deliverResponse.deliverId();

        DeliveryLog deliveryLog = DeliveryLog.of(
            delivery.getId(),
            departure,
            arrival,
            sequence,
            expectedDistance,
            expectedTime,
            deliverId
        );

        deliveryLogRepository.save(deliveryLog);

        return CreateDeliveryResponse.from(delivery);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "deliveryCache", key = "#deliveryId")
    public GetDeliveryResponse getDelivery(UUID deliveryId) {
        Delivery delivery = findDelivery(deliveryId);
        return GetDeliveryResponse.from(delivery);
    }

    @Transactional
    @CachePut(value = "deliveryCache", key = "#request.deliveryId()")
    public UpdateDeliveryResponse updateDelivery(UpdateDeliveryRequest request) {
        if(userContext.getRole().equalsIgnoreCase("ROLE_CLIENT")){
            throw new BadRequestException("권한이 필요합니다");
        }
        Delivery delivery = findDelivery(request.deliveryId());
        delivery.updateStatus(request.status());
        return UpdateDeliveryResponse.from(delivery);
    }

    @Transactional
    @CacheEvict(value = "deliveryCache", key = "#deliveryId")
    public UUID deleteDelivery(UUID deliveryId) {
        if(userContext.getRole().equalsIgnoreCase("ROLE_CLIENT") ||
            userContext.getRole().equalsIgnoreCase("ROLE_DELIVERY")
        ){
            throw new BadRequestException("권한이 필요합니다");
        }
        Delivery delivery = findDelivery(deliveryId);
        delivery.validateDeletable();
        delivery.delete();
        return delivery.getId();
    }

    @Cacheable(value = "searchDeliveryCache", key = "#request.page() + '-' + #request.size()")
    public SearchDeliveryResponse searchOrders(SearchRequest searchRequest) {
        String keyword = searchRequest.keyword();
        String filterKey = searchRequest.filterKey();
        String filterValue = searchRequest.filterValue();
        String sort = searchRequest.sort();
        int page = searchRequest.page();
        int size = searchRequest.size();

        Pagination<Delivery> deliveryPagination = deliveryRepository.searchOrders(keyword,filterKey,filterValue,sort,page,size);

        return SearchDeliveryResponse.from(deliveryPagination);
    }

    private Delivery findDelivery(UUID deliveryId){
        String role = userContext.getRole();
        UUID userId = userContext.getUserId();

        switch (role){
//            TODO : feignclient로 hubid
            case "ROLE_MASTER"->{
                return deliveryRepository.findById(deliveryId)
                    .orElseThrow(()-> new EntityNotFoundException("찾으시는 데이터가 올바르지 않습니다."));
            }
            case "ROLE_HUB"->{
                UUID hubId = UUID.randomUUID();
                return deliveryRepository.findByIdAndHub(deliveryId,hubId);
            }
            case "ROLE_DELIVERY"->{
                return deliveryRepository.findByDeliver(userId);
            }
            default -> {
                throw new BadRequestException("권한이 필요합니다");
            }
        }
    }
}
