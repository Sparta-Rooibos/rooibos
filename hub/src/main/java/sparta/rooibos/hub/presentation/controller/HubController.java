package sparta.rooibos.hub.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.rooibos.hub.application.dto.hub.request.CreateHubRequest;
import sparta.rooibos.hub.application.dto.hub.request.SearchHubRequest;
import sparta.rooibos.hub.application.dto.hub.request.UpdateHubRequest;
import sparta.rooibos.hub.application.dto.hub.response.CreateHubResponse;
import sparta.rooibos.hub.application.dto.hub.response.GetHubResponse;
import sparta.rooibos.hub.application.dto.hub.response.SearchHubResponse;
import sparta.rooibos.hub.application.dto.hub.response.UpdateHubResponse;
import sparta.rooibos.hub.application.service.port.HubService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hub")
public class HubController {

    private final HubService hubService;

    @PostMapping
    public ResponseEntity<CreateHubResponse> createHub(@RequestBody CreateHubRequest createHubRequest) {
        return ResponseEntity.ok(hubService.createHub(createHubRequest));
    }

    @GetMapping("/{hubId}")
    public ResponseEntity<GetHubResponse> getHub(@PathVariable UUID hubId) {
        return ResponseEntity.ok(hubService.getHub(hubId));
    }

    @GetMapping
    public ResponseEntity<SearchHubResponse> searchHub(@ModelAttribute SearchHubRequest searchHubRequest) {
        return ResponseEntity.ok(hubService.searchHub(searchHubRequest));
    }

    @PatchMapping("/{hubId}")
    public ResponseEntity<UpdateHubResponse> updateHub(
            @PathVariable UUID hubId,
            @RequestBody UpdateHubRequest updateHubRequest) {
        return ResponseEntity.ok(hubService.updateHub(hubId, updateHubRequest));
    }

    @PatchMapping("/{hubId}/delete")
    public ResponseEntity<Void> deleteHub(@PathVariable UUID hubId) {
        hubService.deleteHub(hubId);

        return ResponseEntity.noContent().build();
    }
}
