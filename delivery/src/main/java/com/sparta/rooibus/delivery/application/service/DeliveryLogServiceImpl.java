package com.sparta.rooibus.delivery.application.service;

import com.sparta.rooibus.delivery.domain.entity.DeliveryLog;
import com.sparta.rooibus.delivery.domain.repository.DeliveryLogRepository;
import com.sparta.rooibus.delivery.presentation.controller.CreateDeliveryLogRequest;
import com.sparta.rooibus.delivery.presentation.controller.CreateDeliveryLogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryLogServiceImpl {
    private final DeliveryLogRepository deliveryLogRepository;
    public CreateDeliveryLogResponse createDeliveryLog(CreateDeliveryLogRequest request) {
        DeliveryLog deliveryLog = DeliveryLog.of(
            request.deliveryId(),
            request.departure(),
            request.arrival(),
            request.sequence(),
            request.expectedDistance(),
            request.expectedTime(),
            request.deliverId()
        );
        deliveryLogRepository.save(deliveryLog);
        return CreateDeliveryLogResponse.from(deliveryLog);
    }
}
