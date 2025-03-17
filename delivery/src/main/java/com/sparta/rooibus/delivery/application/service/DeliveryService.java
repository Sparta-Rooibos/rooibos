package com.sparta.rooibus.delivery.application.service;

import com.sparta.rooibus.delivery.application.dto.request.CreateDeliveryRequest;
import com.sparta.rooibus.delivery.application.dto.response.CreateDeliveryResponse;
import com.sparta.rooibus.delivery.domain.entity.Delivery;
import com.sparta.rooibus.delivery.domain.repository.DeliveryRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    public CreateDeliveryResponse createDelivery(CreateDeliveryRequest request) {
//       업체 배송담당자 요청해서 받아서 넣기
        String slackAccount = "feign client로 받아올 슬랙 어카운트";
        UUID deliverId = UUID.randomUUID();

        Delivery delivery = request.toCommand(slackAccount,deliverId).toDelivery();
        deliveryRepository.save(delivery);

        CreateDeliveryResponse response = CreateDeliveryResponse.from(delivery);

        return response;
    }
}
