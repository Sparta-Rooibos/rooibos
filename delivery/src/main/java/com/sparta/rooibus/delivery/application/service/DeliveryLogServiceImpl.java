package com.sparta.rooibus.delivery.application.service;

import com.sparta.rooibus.delivery.domain.entity.DeliveryLog;
import com.sparta.rooibus.delivery.domain.repository.DeliveryLogRepository;
import com.sparta.rooibus.delivery.presentation.controller.CreateDeliveryLogRequest;
import com.sparta.rooibus.delivery.presentation.controller.CreateDeliveryLogResponse;
import com.sparta.rooibus.delivery.presentation.controller.GetDeliveryLogResponse;
import com.sparta.rooibus.delivery.presentation.controller.UpdateDeliveryLogRequest;
import com.sparta.rooibus.delivery.presentation.controller.UpdateDeliveryLogResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
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

    public GetDeliveryLogResponse getDeliveryLog(UUID deliveryLogId) {
        DeliveryLog deliveryLog = deliveryLogRepository.findById(deliveryLogId).orElseThrow(
            ()-> new EntityNotFoundException("찾는거 없음")
        );
        return GetDeliveryLogResponse.from(deliveryLog);
    }

    public UpdateDeliveryLogResponse updateDeliveryLog(UpdateDeliveryLogRequest request) {
        UUID deliveryLogId = request.deliveryLogId();
        DeliveryLog deliveryLog = deliveryLogRepository.findById(deliveryLogId).orElseThrow(
            ()-> new EntityNotFoundException("찾는거 없음")
        );
        deliveryLog.setStatus(request.status());
        return UpdateDeliveryLogResponse.from(deliveryLog);
    }
}
