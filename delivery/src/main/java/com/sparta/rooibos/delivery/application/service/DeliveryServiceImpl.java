package com.sparta.rooibos.delivery.application.service;

import com.sparta.rooibos.delivery.application.aop.UserContextRequestBean;
import com.sparta.rooibos.delivery.application.dto.request.CreateDeliveryRequest;
import com.sparta.rooibos.delivery.application.dto.request.SearchRequest;
import com.sparta.rooibos.delivery.application.dto.request.UpdateDeliveryRequest;
import com.sparta.rooibos.delivery.application.dto.request.feign.route.GetRouteRequest;
import com.sparta.rooibos.delivery.application.dto.response.CreateDeliveryResponse;
import com.sparta.rooibos.delivery.application.dto.response.GetDeliveryResponse;
import com.sparta.rooibos.delivery.application.dto.response.SearchDeliveryResponse;
import com.sparta.rooibos.delivery.application.dto.response.UpdateDeliveryResponse;
import com.sparta.rooibos.delivery.application.dto.response.feign.client.GetClientManagerResponse;
import com.sparta.rooibos.delivery.application.dto.response.feign.client.GetClientResponse;
import com.sparta.rooibos.delivery.application.dto.response.feign.route.GetRouteResponse;
import com.sparta.rooibos.delivery.application.service.feign.*;
import com.sparta.rooibos.delivery.domain.entity.Delivery;
import com.sparta.rooibos.delivery.domain.entity.DeliveryLog;
import com.sparta.rooibos.delivery.domain.model.DeliveryStatus;
import com.sparta.rooibos.delivery.domain.model.Pagination;
import com.sparta.rooibos.delivery.domain.repository.DeliveryLogRepository;
import com.sparta.rooibos.delivery.domain.repository.DeliveryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
    private final HubService hubService;

    @Transactional
    public CreateDeliveryResponse createDelivery(CreateDeliveryRequest request) {
        String email = userContext.getEmail();
        String username = userContext.getName();
        String loginUserRole = userContext.getRole();
        String feignRole = "ROLE_MASTER";

        UUID departure = null;
        try {
            departure = clientService.getClient(
                email, username, loginUserRole, request.requestClientId()
            ).getBody().manageHub().id();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        GetClientResponse getClientResponse = null;
        String address = null;
        try {
            getClientResponse = clientService.getClient(
                email, username, loginUserRole, request.receiveClientId()
            ).getBody();
            address = getClientResponse.address();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        UUID recipient = null;
        try {
            GetClientManagerResponse getManagerResponse = clientService.getClientManager(
                email, username, feignRole, request.receiveClientId()
            ).getBody();
            recipient=getManagerResponse.userId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String slackAccount = null;
        try {
            slackAccount = userService.getUser(feignRole, email,recipient).getBody().slackAccount();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        UUID arrival = getClientResponse.manageHub().id();

        GetRouteResponse routeResponse = null;

        try {
            routeResponse = routeService.recommendRoute(GetRouteRequest.of(departure,arrival)).getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Delivery delivery = Delivery.of(
            request.orderId(),
            departure,
            arrival,
            address,
            recipient,
            slackAccount
        );

        deliveryRepository.save(delivery);

        AtomicInteger index = new AtomicInteger(0);
        List<DeliveryLog> deliveryLogs = routeResponse.routeInfos().stream()
            .map(route ->
                DeliveryLog.of(
                    delivery.getId(),
                    route.fromHubId(),
                    route.toHubId(),
                    route.fromHubName(),
                    route.toHubName(),
                    index.incrementAndGet(),
                    String.valueOf(route.distance()),
                    String.valueOf(route.timeCost())
                )).collect(Collectors.toList());

        deliveryLogRepository.saveAll(deliveryLogs);


        return CreateDeliveryResponse.from(delivery);
    }

    @Transactional
    public UUID assignClientDeliver(UUID deliveryId, UUID startHubId){
        String feignRole="ROLE_MASTER";

        UUID clientDeliverId = deliveryAgentService.getDeliver(
            startHubId,"CLIENT",feignRole).deliverId();

        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(
            ()->new IllegalArgumentException("찾는 배송이 없음")
        );
        delivery.setClientDeliver(clientDeliverId);
        return clientDeliverId;
    }

    @Transactional(readOnly = true)
    public GetDeliveryResponse getDelivery(UUID deliveryId) {
        Delivery delivery = findDelivery(deliveryId);
        return GetDeliveryResponse.from(delivery);
    }

    @Transactional
    public UpdateDeliveryResponse updateDelivery(UpdateDeliveryRequest request) {
        if(userContext.getRole().equalsIgnoreCase("ROLE_CLIENT")){
            throw new BadRequestException("권한이 필요합니다");
        }
        Delivery delivery = findDelivery(request.deliveryId());

        delivery.updateStatus(request.status());


        if(request.status().equals(DeliveryStatus.CANCELED)){
            deliveryLogRepository.findAllByDeliveryId(request.deliveryId())
                .forEach(deliveryLog -> {
                    if(deliveryLog.getDeliver()!=null){
                        deliveryAgentService.cancelDeliver(
                            deliveryLog.getDeliver());
                    }
                    deliveryLog.setStatus("CANCELED");}
                );
        }
        return UpdateDeliveryResponse.from(delivery);
    }

    @Transactional
    public UUID deleteDelivery(UUID deliveryId) {
        if(userContext.getRole().equalsIgnoreCase("ROLE_CLIENT") ||
            userContext.getRole().equalsIgnoreCase("ROLE_DELIVERY")
        ){
            throw new BadRequestException("권한이 필요합니다");
        }
        Delivery delivery = findDelivery(deliveryId);
        delivery.validateDeletable();
        delivery.delete(userContext.getName());

        deliveryLogRepository.findAllByDeliveryId(deliveryId)
            .forEach(deliveryLog -> {
                if(deliveryLog.getDeliver()!=null){
                    deliveryAgentService.cancelDeliver(
                        deliveryLog.getDeliver());
                }
                deliveryLog.delete(userContext.getName());
            });

        return delivery.getId();
    }

    public SearchDeliveryResponse searchDeliveries(SearchRequest searchRequest) {
        String keyword = searchRequest.keyword();
        String filterKey = searchRequest.filterKey();
        String filterValue = searchRequest.filterValue();
        String sort = searchRequest.sort();
        int page = searchRequest.page();
        int size = searchRequest.size();

        Pagination<Delivery> deliveryPagination = searchDeliveries(keyword,filterKey,filterValue,sort,page,size);

        return SearchDeliveryResponse.from(deliveryPagination);
    }

    private Delivery findDelivery(UUID deliveryId){
        String role = userContext.getRole();
        String userEmail = userContext.getEmail();
        UUID userId = userContext.getUserId();// 유저 페인 클라이언트에서 이메일로 userId를 알아오기
        switch (role){
//            TODO : feignclient로 hubid
            case "ROLE_MASTER"->{
                return deliveryRepository.findById(deliveryId)
                    .orElseThrow(()-> new EntityNotFoundException("찾으시는 데이터가 올바르지 않습니다."));
            }
            case "ROLE_HUB"->{
                UUID hubId = hubService.getHubByUser(userId,role).getBody();
                return deliveryRepository.findByIdAndHub(deliveryId,hubId);
            }
            case "ROLE_DELIVERY"->{
//                업체 배송자 전용임.
                return deliveryRepository.findByDeliver(userId,deliveryId);
            }
            case "ROLE_CLIENT"->{
                return deliveryRepository.findById(deliveryId)
                    .orElseThrow(()-> new EntityNotFoundException("찾으시는 데이터가 올바르지 않습니다."));
            }
            default -> {
                throw new BadRequestException("권한이 필요합니다");
            }
        }
    }

    private Pagination<Delivery> searchDeliveries(String keyword,String filterKey,String filterValue,String sort,int page,int size){
        String role = userContext.getRole();
        UUID userId = userContext.getUserId();
        switch (role){
            case "ROLE_MASTER", "ROLE_CLIENT" ->{
                return deliveryRepository.searchDeliveries(keyword,filterKey,filterValue,sort,page,size);
            }
            case "ROLE_HUB"->{
                UUID hubId = hubService.getHubByUser(userId,role).getBody();
                return deliveryRepository.findAllByDeparture(hubId,keyword,filterKey,filterValue,sort,page,size);
            }
            case "ROLE_DELIVERY"->{
//                업체 배송자 전용 검색
                UUID deliverId = UUID.randomUUID();
                return deliveryRepository.searchDeliveriesByDeliver(deliverId,keyword,filterKey,filterValue,sort,page,size);
            }
            default -> {
                throw new BadRequestException("권한이 필요합니다");
            }
        }
    }
}
