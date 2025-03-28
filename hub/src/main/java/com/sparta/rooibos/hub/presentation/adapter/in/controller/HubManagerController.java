package com.sparta.rooibos.hub.presentation.adapter.in.controller;

import com.sparta.rooibos.hub.application.aop.RoleCheck;
import com.sparta.rooibos.hub.application.dto.hubManager.request.CreateHubManagerRequest;
import com.sparta.rooibos.hub.application.dto.hubManager.response.CreateHubManagerResponse;
import com.sparta.rooibos.hub.application.port.in.HubManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hubManager")
public class HubManagerController {

    private final HubManagerService hubManagerService;

    @RoleCheck({"MASTER"})
    @PostMapping
    public ResponseEntity<CreateHubManagerResponse> createHubManager(
            @RequestBody CreateHubManagerRequest createHubManagerRequest
    ) {
        return ResponseEntity.ok(hubManagerService.createHubManager(createHubManagerRequest));
    }

    @GetMapping("/userId")
    public ResponseEntity<UUID> getHubIdByUserId(@RequestParam UUID userId) {
        return ResponseEntity.ok(hubManagerService.getHubIdByUserId(userId));
    }

    @GetMapping("/email")
    public ResponseEntity<UUID> getHubIdByEmail(@RequestParam String email) {
        return ResponseEntity.ok(hubManagerService.getHubIdByEmail(email));
    }

    @RoleCheck({"MASTER"})
    @PatchMapping("/{userId}")
    public ResponseEntity<Void> deleteHubManager(@PathVariable("userId") UUID userId) {
        hubManagerService.deleteHubManager(userId);

        return ResponseEntity.noContent().build();
    }
}
