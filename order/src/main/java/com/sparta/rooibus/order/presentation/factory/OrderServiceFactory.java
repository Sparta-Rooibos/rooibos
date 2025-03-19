package com.sparta.rooibus.order.presentation.factory;

import com.sparta.rooibus.order.application.service.ClientOrderService;
import com.sparta.rooibus.order.application.service.DeliveryOrderService;
import com.sparta.rooibus.order.application.service.HubOrderService;
import com.sparta.rooibus.order.application.service.MasterOrderService;
import com.sparta.rooibus.order.application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceFactory {
    private final MasterOrderService orderService;
    private final HubOrderService hubOrderService;
    private final ClientOrderService clientOrderService;
    private final DeliveryOrderService deliveryOrderService;
// TODO : 각 역할에 맞는 서비스 배정
    public OrderService getService(String role) {
        if ("Role_Master".equals(role)) {
            return orderService;
        } else if("Role_HubMANAGER".equals(role)) {
//            허브 매니저일때 서비스
            return hubOrderService;
        } else if("Role_DeliveryManager".equals(role)) {
//            배송 매니저일때 서비스
            return deliveryOrderService;
        } else if("Role_ClientManager".equals(role)) {
//            업체 담당자일때 서비스
            return clientOrderService;
        }
        throw new IllegalArgumentException("권한이 없는 사용자");
    }
}
