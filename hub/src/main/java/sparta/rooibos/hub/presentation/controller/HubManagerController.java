package sparta.rooibos.hub.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.rooibos.hub.application.dto.hubManager.request.CreateHubManagerRequest;
import sparta.rooibos.hub.application.dto.hubManager.response.CreateHubManagerResponse;
import sparta.rooibos.hub.application.dto.hubManager.response.GetHubManagerHubIdResponse;
import sparta.rooibos.hub.application.service.port.in.HubManagerService;

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

    @GetMapping(params = "userId")
    public ResponseEntity<GetHubManagerHubIdResponse> getHubIdByUserId(@RequestParam UUID userId) {
        return ResponseEntity.ok(hubManagerService.getHubIdByUserId(userId));
    }

    @GetMapping(params = "username")
    public ResponseEntity<GetHubManagerHubIdResponse> getHubIdByUserMame(@RequestParam String username) {
        return ResponseEntity.ok(hubManagerService.getHubIdByUsername(username));
    }

    @PatchMapping
    public ResponseEntity<Void> deleteHubManager(@RequestParam UUID userId) {
        hubManagerService.deleteHubManager(userId);

        return ResponseEntity.noContent().build();
    }
}
