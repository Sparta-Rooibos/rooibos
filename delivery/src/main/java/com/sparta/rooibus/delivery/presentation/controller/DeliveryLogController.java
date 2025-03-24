package com.sparta.rooibus.delivery.presentation.controller;

import com.sparta.rooibus.delivery.application.dto.request.CreateDeliveryLogRequest;
import com.sparta.rooibus.delivery.application.dto.request.SearchRequest;
import com.sparta.rooibus.delivery.application.dto.request.UpdateDeliveryLogRequest;
import com.sparta.rooibus.delivery.application.dto.response.CreateDeliveryLogResponse;
import com.sparta.rooibus.delivery.application.dto.response.DeleteDeliveryLogResponse;
import com.sparta.rooibus.delivery.application.dto.response.GetDeliveryLogResponse;
import com.sparta.rooibus.delivery.application.dto.response.SearchDeliveryLogResponse;
import com.sparta.rooibus.delivery.application.dto.response.UpdateDeliveryLogResponse;
import com.sparta.rooibus.delivery.application.service.DeliveryLogServiceImpl;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/deliveryLog")
@RequiredArgsConstructor
public class DeliveryLogController {

    private final DeliveryLogServiceImpl deliveryLogService;

    @PostMapping
    public ResponseEntity<CreateDeliveryLogResponse> createDeliveryLog(@Valid @RequestBody CreateDeliveryLogRequest request){
        return ResponseEntity.ok(deliveryLogService.createDeliveryLog(request));
    }

    @GetMapping("/{deliveryLogId}")
    public ResponseEntity<GetDeliveryLogResponse> getDeliveryLog(@PathVariable UUID deliveryLogId){
        return ResponseEntity.ok(deliveryLogService.getDeliveryLog(deliveryLogId));
    }

    @PutMapping
    public ResponseEntity<UpdateDeliveryLogResponse> updateDeliveryLog(@Valid @RequestBody UpdateDeliveryLogRequest request){
        return ResponseEntity.ok(deliveryLogService.updateDeliveryLog(request));
    }

    @PatchMapping("/{deliveryLogId}")
    public ResponseEntity<DeleteDeliveryLogResponse> deleteDeliveryLog(@PathVariable UUID deliveryLogId){
        return ResponseEntity.ok(deliveryLogService.deleteDeliveryLog(deliveryLogId));
    }

    @GetMapping("/search")
    public ResponseEntity<SearchDeliveryLogResponse> searchDeliveryLogs(
        @ModelAttribute SearchRequest request
    ){
        return ResponseEntity.ok(deliveryLogService.searchDeliveryLogs(request));
    }


}
