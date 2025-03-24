package com.sparta.rooibos.deliverer.presentation.controller;

import com.sparta.rooibos.deliverer.application.dto.request.DelivererRequest;
import com.sparta.rooibos.deliverer.application.dto.request.DelivererSearchRequest;
import com.sparta.rooibos.deliverer.application.dto.response.DelivererListResponse;
import com.sparta.rooibos.deliverer.application.dto.response.DelivererResponse;
import com.sparta.rooibos.deliverer.application.service.port.DelivererService;
import com.sparta.rooibos.deliverer.infrastructure.aop.RoleCheck;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deliverers")
@RequiredArgsConstructor
public class DelivererController {
    private final DelivererService delivererService;

    @PostMapping
    @RoleCheck({"ROLE_MASTER, ROLE_HUB"})
    public ResponseEntity<DelivererResponse> createDeliverer(@RequestBody @Valid DelivererRequest request) {
        return ResponseEntity.ok(delivererService.createDeliverer(request));
    }

    @PutMapping("/{id}")
    @RoleCheck({"ROLE_MASTER, ROLE_HUB"})
    public ResponseEntity<DelivererResponse> updateDeliverer(
            @PathVariable UUID id,
            @RequestBody @Valid DelivererRequest request) {
        return ResponseEntity.ok(delivererService.updateDeliverer(id, request));
    }

    @RoleCheck({"ROLE_MASTER, ROLE_HUB, ROLE_DELIBERY"})
    @GetMapping("/{id}")
    public ResponseEntity<DelivererResponse> getDeliverer(@PathVariable UUID id) {
        return ResponseEntity.ok(delivererService.getDeliverer(id));
    }

    @PatchMapping("/{id}")
    @RoleCheck({"ROLE_MASTER, ROLE_HUB"})
    public ResponseEntity<Void> deleteDeliverer(@PathVariable UUID id) {
        delivererService.deleteDeliverer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @RoleCheck({"ROLE_MASTER, ROLE_HUB, ROLE_DELIBERY"})
    public ResponseEntity<DelivererListResponse> searchDeliverers(@ModelAttribute DelivererSearchRequest request) {
        return ResponseEntity.ok(delivererService.searchDeliverers(request));
    }
}