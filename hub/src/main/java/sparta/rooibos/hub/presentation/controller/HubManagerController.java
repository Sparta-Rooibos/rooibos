package sparta.rooibos.hub.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.rooibos.hub.application.dto.hubManager.request.CreateHubManagerRequest;
import sparta.rooibos.hub.application.dto.hubManager.response.CreateHubManagerResponse;
import sparta.rooibos.hub.application.dto.hubManager.response.GetHubManagerHubIdResponse;
import sparta.rooibos.hub.application.port.in.HubManagerService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hubManager")
public class HubManagerController {

    private final HubManagerService hubManagerService;

    @PostMapping
    public ResponseEntity<CreateHubManagerResponse> createHubManager(
            @RequestBody CreateHubManagerRequest createHubManagerRequest
    ) {
        return ResponseEntity.ok(hubManagerService.createHubManager(createHubManagerRequest));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<GetHubManagerHubIdResponse> getHubIdByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(hubManagerService.getHubIdByUserId(userId));
    }

    @GetMapping("/{email}")
    public ResponseEntity<GetHubManagerHubIdResponse> getHubIdByEmail(@PathVariable String email) {
        return ResponseEntity.ok(hubManagerService.getHubIdByEmail(email));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Void> deleteHubManager(@PathVariable UUID userId) {
        hubManagerService.deleteHubManager(userId);

        return ResponseEntity.noContent().build();
    }
}
