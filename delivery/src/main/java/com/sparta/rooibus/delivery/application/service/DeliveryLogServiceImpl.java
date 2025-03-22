package com.sparta.rooibus.delivery.application.service;

import com.sparta.rooibus.delivery.application.dto.request.SearchRequest;
import com.sparta.rooibus.delivery.domain.entity.DeliveryLog;
import com.sparta.rooibus.delivery.domain.model.Pagination;
import com.sparta.rooibus.delivery.domain.repository.DeliveryLogRepository;
import com.sparta.rooibus.delivery.application.dto.request.CreateDeliveryLogRequest;
import com.sparta.rooibus.delivery.application.dto.response.CreateDeliveryLogResponse;
import com.sparta.rooibus.delivery.application.dto.response.DeleteDeliveryLogResponse;
import com.sparta.rooibus.delivery.application.dto.response.GetDeliveryLogResponse;
import com.sparta.rooibus.delivery.application.dto.response.SearchDeliveryLogResponse;
import com.sparta.rooibus.delivery.application.dto.request.UpdateDeliveryLogRequest;
import com.sparta.rooibus.delivery.application.dto.response.UpdateDeliveryLogResponse;
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

    public DeleteDeliveryLogResponse deleteDeliveryLog(UUID deliveryLogId) {
        DeliveryLog deliveryLog = deliveryLogRepository.findById(deliveryLogId).orElseThrow(
            ()-> new EntityNotFoundException("찾는거 없음")
        );
        deliveryLog.delete();
        return DeleteDeliveryLogResponse.from(deliveryLog);
    }

    public SearchDeliveryLogResponse searchDeliveryLogs(SearchRequest searchRequest) {
        String keyword = searchRequest.keyword();
        String filterKey = searchRequest.filterKey();
        String filterValue = searchRequest.filterValue();
        String sort = searchRequest.sort();
        int page = searchRequest.page();
        int size = searchRequest.size();

        Pagination<DeliveryLog> deliveryPagination = deliveryLogRepository.searchDeliveryLogs(keyword,filterKey,filterValue,sort,page,size);

        return SearchDeliveryLogResponse.from(deliveryPagination);
    }
}
