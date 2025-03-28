package com.sparta.rooibos.delivery.application.service;

import com.sparta.rooibos.delivery.application.aop.UserContextRequestBean;
import com.sparta.rooibos.delivery.application.dto.request.CreateDeliveryLogRequest;
import com.sparta.rooibos.delivery.application.dto.request.SearchRequest;
import com.sparta.rooibos.delivery.application.dto.request.UpdateDeliveryLogRequest;
import com.sparta.rooibos.delivery.application.dto.response.*;
import com.sparta.rooibos.delivery.domain.entity.DeliveryLog;
import com.sparta.rooibos.delivery.domain.model.Pagination;
import com.sparta.rooibos.delivery.domain.repository.DeliveryLogRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryLogServiceImpl {
    private final DeliveryLogRepository deliveryLogRepository;
    private final UserContextRequestBean userContext;

    public CreateDeliveryLogResponse createDeliveryLog(CreateDeliveryLogRequest request) {
        if(userContext.getRole().equalsIgnoreCase("ROLE_MASTER")){
            throw new RuntimeException("권한이 마스터여야 합니다.");
        }
        DeliveryLog deliveryLog = DeliveryLog.of(
            request.departure(),
            request.arrival(),
            request.departureName(),
            request.arrivalName(),
            request.sequence(),
            request.expectedDistance(),
            request.expectedTime()
        );
        deliveryLogRepository.save(deliveryLog);
        return CreateDeliveryLogResponse.from(deliveryLog);
    }

    public GetDeliveryLogResponse getDeliveryLog(UUID deliveryLogId) {
        if(userContext.getRole().equalsIgnoreCase("ROLE_MASTER")){
            throw new RuntimeException("권한이 마스터여야 합니다.");
        }
        DeliveryLog deliveryLog = deliveryLogRepository.findById(deliveryLogId).orElseThrow(
            ()-> new EntityNotFoundException("찾는거 없음")
        );
        return GetDeliveryLogResponse.from(deliveryLog);
    }

    public UpdateDeliveryLogResponse updateDeliveryLog(UpdateDeliveryLogRequest request) {
        if(userContext.getRole().equalsIgnoreCase("ROLE_MASTER")){
            throw new RuntimeException("권한이 마스터여야 합니다.");
        }
        UUID deliveryLogId = request.deliveryLogId();
        DeliveryLog deliveryLog = deliveryLogRepository.findById(deliveryLogId).orElseThrow(
            ()-> new EntityNotFoundException("찾는거 없음")
        );
        deliveryLog.setStatus(request.status());
        return UpdateDeliveryLogResponse.from(deliveryLog);
    }

    public DeleteDeliveryLogResponse deleteDeliveryLog(UUID deliveryLogId) {
        if(userContext.getRole().equalsIgnoreCase("ROLE_MASTER")){
            throw new RuntimeException("권한이 마스터여야 합니다.");
        }
        DeliveryLog deliveryLog = deliveryLogRepository.findById(deliveryLogId).orElseThrow(
            ()-> new EntityNotFoundException("찾는거 없음")
        );
        deliveryLog.delete(userContext.getEmail());
        return DeleteDeliveryLogResponse.from(deliveryLog);
    }

    public SearchDeliveryLogResponse searchDeliveryLogs(SearchRequest searchRequest) {
        if(userContext.getRole().equalsIgnoreCase("ROLE_MASTER")){
            throw new RuntimeException("권한이 마스터여야 합니다.");
        }
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
