package com.sparta.rooibos.deliverer.application.service.port;

import com.sparta.rooibos.deliverer.application.dto.request.DelivererRequest;
import com.sparta.rooibos.deliverer.application.dto.request.DelivererSearchRequest;
import com.sparta.rooibos.deliverer.application.dto.response.DelivererListResponse;
import com.sparta.rooibos.deliverer.application.dto.response.DelivererResponse;
import com.sparta.rooibos.deliverer.domain.entity.DelivererType;

import java.util.UUID;

public interface DelivererService {
    DelivererResponse createDeliverer(DelivererRequest request);
    DelivererResponse updateDeliverer(UUID delivererId, DelivererRequest request);
    void deleteDeliverer(UUID delivererId);
    DelivererResponse getDeliverer(UUID delivererId);
    DelivererListResponse searchDeliverers(DelivererSearchRequest request);
    DelivererResponse assignNextDeliverer(UUID hubId, DelivererType type);
    void cancelAssignment(UUID delivererId);
}
