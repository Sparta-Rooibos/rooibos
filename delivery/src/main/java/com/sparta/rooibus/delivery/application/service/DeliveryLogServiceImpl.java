package com.sparta.rooibus.delivery.application.service;

import com.sparta.rooibus.delivery.application.aop.UserContextRequestBean;
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
