package com.sparta.rooibos.deliverer.application.service.port;

import com.sparta.rooibos.deliverer.application.dto.request.DelivererRequest;
import com.sparta.rooibos.deliverer.application.dto.request.DelivererSearchRequest;
import com.sparta.rooibos.deliverer.application.dto.response.DelivererListResponse;
import com.sparta.rooibos.deliverer.application.dto.response.DelivererResponse;

import java.util.UUID;

public interface DelivererService {
    DelivererResponse createDeliverer(DelivererRequest request);
    DelivererResponse updateDeliverer(UUID id, DelivererRequest request);
    void deleteDeliverer(UUID id);
    DelivererResponse getDeliverer(UUID id);
    DelivererListResponse searchDeliverers(DelivererSearchRequest request);
}
