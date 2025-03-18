package com.sparta.rooibus.delivery.presentation.controller;

import com.sparta.rooibus.delivery.application.dto.request.CreateDeliveryRequest;
import com.sparta.rooibus.delivery.application.dto.request.UpdateDeliveryRequest;
import com.sparta.rooibus.delivery.application.dto.response.CreateDeliveryResponse;
import com.sparta.rooibus.delivery.application.dto.response.GetDeliveryResponse;
import com.sparta.rooibus.delivery.application.dto.response.UpdateDeliveryResponse;
import com.sparta.rooibus.delivery.application.service.DeliveryService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// [CRUD][도메인][req/res][pre-dto/app x] dto 규칙
@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<CreateDeliveryResponse> createDelivery(@Valid @RequestBody CreateDeliveryRequest request){
        CreateDeliveryResponse response = deliveryService.createDelivery(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{deliveryId}")
    public ResponseEntity<GetDeliveryResponse> getDelivery(@PathVariable("deliveryId") UUID deliveryId){
        GetDeliveryResponse response = deliveryService.getDelivery(deliveryId);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<UpdateDeliveryResponse> updateDelivery(@Valid @RequestBody UpdateDeliveryRequest request){
        UpdateDeliveryResponse response = deliveryService.updateDelivery(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{deliveryId}")
    public ResponseEntity<UUID> deleteDelivery(@PathVariable("deliveryId") UUID deliveryId){
        UUID response = deliveryService.deleteDelivery(deliveryId);
        return ResponseEntity.ok(response);
    }
}
