package com.sparta.rooibus.delivery.application.service;

import com.sparta.rooibus.delivery.application.aop.UserContextRequestBean;
import com.sparta.rooibus.delivery.application.dto.request.CreateDeliveryRequest;
import com.sparta.rooibus.delivery.application.dto.request.SearchRequest;
import com.sparta.rooibus.delivery.application.dto.request.UpdateDeliveryRequest;
import com.sparta.rooibus.delivery.application.dto.request.feign.deliverAgent.GetDeliverRequest;
import com.sparta.rooibus.delivery.application.dto.request.feign.deliverAgent.GetHubDeliverRequest;
import com.sparta.rooibus.delivery.application.dto.request.feign.route.GetRouteRequest;
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
import com.sparta.rooibus.delivery.domain.model.DeliveryStatus;
import com.sparta.rooibus.delivery.domain.model.Pagination;
import com.sparta.rooibus.delivery.domain.repository.DeliveryLogRepository;
import com.sparta.rooibus.delivery.domain.repository.DeliveryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.BadRequestException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
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
//        TODO : 배송자 지정 서비스, 루트 서비스 실제 로직으로 변경하기
        String email = userContext.getEmail();
        String username = userContext.getName();
        String loginUserRole = userContext.getRole();
        String feignRole = "ROLE_MASTER";

        UUID departure = null;
        GetClientResponse getClientResponse = null;
        UUID recipient = null;
        String slackAccount = null;
        UUID clientDeliverId = null;
        try {
            departure = clientService.getClient(
                email, username, loginUserRole, request.requestClientId()
            ).getBody().manageHub().id();

            getClientResponse = clientService.getClient(
                email, username, loginUserRole, request.receiveClientId()
            ).getBody();

            recipient = clientService.getClientManager(
                email, username, feignRole, request.receiveClientId()
            ).getBody().clientManagerId();

            slackAccount = userService.getUser(feignRole, recipient).getBody().slackAccount();

            clientDeliverId = deliveryAgentService.getDeliver(
                GetDeliverRequest.from(request.requestClientId())).deliverId();
        } catch (Exception e) {
            throw new RuntimeException("");
        }

        UUID arrival = getClientResponse.manageHub().id();
        String address = getClientResponse.address();

        Delivery delivery = Delivery.of(
            request.orderId(),
            departure,
            arrival,
            address,
            recipient,
            slackAccount,
            clientDeliverId
        );

        GetRouteResponse routeResponse = null;
        try {
            routeResponse = routeService.getRoute(departure, arrival).getBody();
        } catch (Exception e) {
//           TODO : 업체 배송 담당자 지정하는 부분에서 뭔가를 바꿧다면 그걸 다시 바꾸는 로직 추가
            throw new RuntimeException(e);
        }
        AtomicInteger index = new AtomicInteger(0);
        List<DeliveryLog> deliveryLogs;
        try {
            GetHubDeliverResponse deliverResponse = deliveryAgentService.getHubDeliver(
                GetHubDeliverRequest.from(departure));
            UUID deliverId = deliverResponse.deliverId();

            deliveryLogs = routeResponse.routeList().stream()
                .map(route ->
                    DeliveryLog.of(
                        route.fromHubId(),
                        route.toHubId(),
                        index.incrementAndGet(),
                        route.distance().toString(),
                        route.timeCost().toString(),
                        deliverId
                    )).collect(Collectors.toList());
        } catch (Exception e) {
//           TODO : 업체 배송 담당자 지정하는 부분에서 뭔가를 바꿧다면 그걸 다시 바꾸는 로직 추가
//           TODO : 허브 배송 담당자 지정하는 부분에서 뭔가를 바꿧다면 그걸 다시 바꾸는 로직 추가
            throw new RuntimeException(e);
        }
        deliveryLogRepository.saveAll(deliveryLogs);
        deliveryRepository.save(delivery);

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


        if(request.status().equals(DeliveryStatus.CANCELED)){
            deliveryLogRepository.findAllByDeliveryId(request.deliveryId())
                .forEach(deliveryLog -> deliveryLog.setStatus("CANCELED"));
        }
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
        delivery.delete(userContext.getName());

        deliveryLogRepository.findAllByDeliveryId(deliveryId)
            .forEach(deliveryLog -> deliveryLog.delete(userContext.getName()));

        return delivery.getId();
    }

    @Cacheable(value = "searchDeliveryCache", key = "#request.page() + '-' + #request.size()")
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
        UUID userId = UUID.randomUUID();// 유저 페인 클라이언트에서 이메일로 userId를 알아오기
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
//                TODO : 허브 배송자 인지, 업체 배송자인지 확인을 페인 클라이언트 처리
//                role이 허브 배송자 이면 로그 테이블에서
//                배송을 맡은 사람이 로그인한 사람의 userId인 것들을 찾고
//                그 중에서 찾는 deliveryId와 일치하는 값이 있으면
//                배송 테이블에서 deliveryId를 통해 배송을 찾아서 보여줌
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
        String userEmail = userContext.getEmail();
        String userName = userContext.getName();
        UUID userId = userContext.getUserId();

        switch (role){
//            TODO : feign client로 hubId
            case "ROLE_MASTER"->{
                return deliveryRepository.searchDeliveries(keyword,filterKey,filterValue,sort,page,size);
            }
            case "ROLE_HUB"->{
                UUID hubId = UUID.randomUUID(); // TODO : 로그인한 사람의 소속 허브를 가져옴
                return deliveryRepository.findAllByDeparture(hubId,keyword,filterKey,filterValue,sort,page,size);
            }
            case "ROLE_DELIVERY"->{
//                TODO : 허브 배송자 인지, 업체 배송자인지 확인을 페인 클라이언트로 해야함.
//                role이 허브 배송자 이면 로그 테이블에서
//                배송을 맡은 사람이 로그인한 사람의 userId인 것들을 찾고
//                그 중에서 찾는 deliveryId와 일치하는 값이 있으면
//                배송 테이블에서 deliveryId를 통해 배송을 찾아서 보여줌
                UUID deliverId = UUID.randomUUID();
                return deliveryRepository.searchDeliveriesByDeliver(deliverId,keyword,filterKey,filterValue,sort,page,size);
            }
            case "ROLE_CLIENT"->{
                return deliveryRepository.searchDeliveries(keyword,filterKey,filterValue,sort,page,size);
            }
            default -> {
                throw new BadRequestException("권한이 필요합니다");
            }
        }
    }
}
