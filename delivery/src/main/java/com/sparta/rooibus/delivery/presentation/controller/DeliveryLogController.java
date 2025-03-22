package com.sparta.rooibus.delivery.presentation.controller;

import com.sparta.rooibus.delivery.application.dto.request.CreateDeliveryRequest;
import com.sparta.rooibus.delivery.application.service.DeliveryLogServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
        CreateDeliveryLogResponse response = deliveryLogService.createDeliveryLog(request);
        return ResponseEntity.ok(response);
    }


}
