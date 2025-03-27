package com.sparta.rooibus.delivery.presentation.controller;

import com.sparta.rooibus.delivery.application.dto.request.CreateDeliveryLogRequest;
import com.sparta.rooibus.delivery.application.dto.request.SearchRequest;
import com.sparta.rooibus.delivery.application.dto.request.UpdateDeliveryLogRequest;
import com.sparta.rooibus.delivery.application.dto.response.*;
import com.sparta.rooibus.delivery.application.service.DeliveryLogServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
