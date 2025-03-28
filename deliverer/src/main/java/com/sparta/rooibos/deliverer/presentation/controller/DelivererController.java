package com.sparta.rooibos.deliverer.presentation.controller;

import com.sparta.rooibos.deliverer.application.dto.request.DelivererRequest;
import com.sparta.rooibos.deliverer.application.dto.request.DelivererSearchRequest;
import com.sparta.rooibos.deliverer.application.dto.response.DelivererListResponse;
import com.sparta.rooibos.deliverer.application.dto.response.DelivererResponse;
import com.sparta.rooibos.deliverer.application.service.port.DelivererService;

import com.sparta.rooibos.deliverer.application.aop.RoleCheck;

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

    @PutMapping("/{delivererId}")
    @RoleCheck({"ROLE_MASTER, ROLE_HUB"})
    public ResponseEntity<DelivererResponse> updateDeliverer(
            @PathVariable UUID delivererId,
            @RequestBody @Valid DelivererRequest request) {
        return ResponseEntity.ok(delivererService.updateDeliverer(delivererId, request));
    }

    @RoleCheck({"ROLE_MASTER, ROLE_HUB, ROLE_DELIBERY"})
    @GetMapping("/{delivererId}")
    public ResponseEntity<DelivererResponse> getDeliverer(@PathVariable UUID delivererId) {
        return ResponseEntity.ok(delivererService.getDeliverer(delivererId));
    }

    @PatchMapping("/{delivererId}")
    @RoleCheck({"ROLE_MASTER, ROLE_HUB"})
    public ResponseEntity<Void> deleteDeliverer(@PathVariable UUID delivererId) {
        delivererService.deleteDeliverer(delivererId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @RoleCheck({"ROLE_MASTER, ROLE_HUB, ROLE_DELIBERY"})
    public ResponseEntity<DelivererListResponse> searchDeliverers(@ModelAttribute DelivererSearchRequest request) {
        return ResponseEntity.ok(delivererService.searchDeliverers(request));
    }

    @GetMapping("/assign")
    @RoleCheck({"ROLE_MASTER, ROLE_HUB, ROLE_DELIBERY"})
    public ResponseEntity<DelivererResponse> assignDeliverer(
            @RequestParam UUID hubId,
            @RequestParam String type
    ) {
        DelivererResponse assigned = delivererService.assignNextDeliverer(hubId, type);
        return ResponseEntity.ok(assigned);
    }

    @PatchMapping("/unassign/{delivererId}")
    @RoleCheck({"ROLE_MASTER, ROLE_HUB, ROLE_DELIBERY"})
    public ResponseEntity<Void> cancelAssignment(
            @PathVariable UUID delivererId
    ) {
        delivererService.cancelAssignment(delivererId);
        return ResponseEntity.ok().build();
    }
}