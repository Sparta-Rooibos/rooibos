package com.sparta.rooibus.delivery.presentation.controller;

import com.sparta.rooibus.delivery.application.dto.request.CreateDeliveryRequest;
import com.sparta.rooibus.delivery.application.dto.request.SearchRequest;
import com.sparta.rooibus.delivery.application.dto.request.UpdateDeliveryRequest;
import com.sparta.rooibus.delivery.application.dto.response.CreateDeliveryResponse;
import com.sparta.rooibus.delivery.application.dto.response.GetDeliveryResponse;
import com.sparta.rooibus.delivery.application.dto.response.SearchDeliveryResponse;
import com.sparta.rooibus.delivery.application.dto.response.UpdateDeliveryResponse;
import com.sparta.rooibus.delivery.application.service.DeliveryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

// [CRUD][도메인][req/res][pre-dto/app x] dto 규칙
@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryServiceImpl deliveryServiceImpl;

    @PostMapping
    public ResponseEntity<CreateDeliveryResponse> createDelivery(@Valid @RequestBody CreateDeliveryRequest request){
        CreateDeliveryResponse response = deliveryServiceImpl.createDelivery(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{deliveryId}")
    public ResponseEntity<GetDeliveryResponse> getDelivery(@PathVariable("deliveryId") UUID deliveryId){
        GetDeliveryResponse response = deliveryServiceImpl.getDelivery(deliveryId);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<UpdateDeliveryResponse> updateDelivery(@Valid @RequestBody UpdateDeliveryRequest request){
        UpdateDeliveryResponse response = deliveryServiceImpl.updateDelivery(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{deliveryId}")
    public ResponseEntity<UUID> deleteDelivery(@PathVariable("deliveryId") UUID deliveryId){
        UUID response = deliveryServiceImpl.deleteDelivery(deliveryId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<SearchDeliveryResponse> searchOrders(
        @ModelAttribute SearchRequest request
        ) {
        return ResponseEntity.ok(deliveryServiceImpl.searchDeliveries(request));
    }

    @PostMapping("/assignDeliver/{deliveryId}")
    public ResponseEntity<UUID> assignDeliver(@PathVariable UUID deliveryId,@RequestParam UUID startHubId){
        return ResponseEntity.ok(deliveryServiceImpl.assignClientDeliver(deliveryId,startHubId));
    }
}
