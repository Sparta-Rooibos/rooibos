package com.sparta.rooibus.delivery.application.service;

import com.sparta.rooibus.delivery.application.dto.request.CreateDeliveryRequest;
import com.sparta.rooibus.delivery.application.dto.request.UpdateDeliveryRequest;
import com.sparta.rooibus.delivery.application.dto.response.CreateDeliveryResponse;
import com.sparta.rooibus.delivery.application.dto.response.GetDeliveryResponse;
import com.sparta.rooibus.delivery.application.dto.response.UpdateDeliveryResponse;
import com.sparta.rooibus.delivery.domain.entity.Delivery;
import com.sparta.rooibus.delivery.domain.repository.DeliveryRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    @Transactional
    public CreateDeliveryResponse createDelivery(CreateDeliveryRequest request) {
//       업체 배송담당자 요청해서 받아서 넣기
        String slackAccount = "feign client로 받아올 슬랙 어카운트";
        UUID deliverId = UUID.randomUUID();

        Delivery delivery = request.toCommand(slackAccount,deliverId).toDelivery();
        deliveryRepository.save(delivery);

        return CreateDeliveryResponse.from(delivery);
    }

    @Transactional(readOnly = true)
    public GetDeliveryResponse getDelivery(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(
            ()-> new EntityNotFoundException("찾으시는 데이터가 올바르지 않습니다.")
        );
        return GetDeliveryResponse.from(delivery);
    }

    @Transactional
    public UpdateDeliveryResponse updateDelivery(UpdateDeliveryRequest request) {
        Delivery delivery = deliveryRepository.findById(request.deliveryId()).orElseThrow(
            ()-> new EntityNotFoundException("찾으시는 데이터가 올바르지 않습니다.")
        );
        delivery.update(request);
        return UpdateDeliveryResponse.from(delivery);
    }

    @Transactional
    public UUID deleteDelivery(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(
            ()-> new EntityNotFoundException("찾으시는 데이터가 올바르지 않습니다.")
        );
        delivery.validateDeletable();
        delivery.delete();
        return delivery.getId();
    }
}
