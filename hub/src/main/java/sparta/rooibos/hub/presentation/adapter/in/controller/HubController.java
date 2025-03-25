package sparta.rooibos.hub.presentation.adapter.in.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.rooibos.hub.application.aop.RoleCheck;
import sparta.rooibos.hub.application.dto.hub.request.CreateHubRequest;
import sparta.rooibos.hub.application.dto.hub.request.SearchHubRequest;
import sparta.rooibos.hub.application.dto.hub.request.UpdateHubRequest;
import sparta.rooibos.hub.application.dto.hub.response.CreateHubResponse;
import sparta.rooibos.hub.application.dto.hub.response.GetHubResponse;
import sparta.rooibos.hub.application.dto.hub.response.SearchHubResponse;
import sparta.rooibos.hub.application.dto.hub.response.UpdateHubResponse;
import sparta.rooibos.hub.application.port.in.HubService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hub")
public class HubController {

    private final HubService hubService;

    @RoleCheck({"MASTER"})
    @PostMapping
    public ResponseEntity<CreateHubResponse> createHub(@RequestBody CreateHubRequest createHubRequest) {
        return ResponseEntity.ok(hubService.createHub(createHubRequest));
    }

    @GetMapping("/{hubId}")
    public ResponseEntity<GetHubResponse> getHub(@PathVariable("hubId") UUID hubId) {
        return ResponseEntity.ok(hubService.getHub(hubId));
    }

    @GetMapping("/{hubId}/check")
    public ResponseEntity<Boolean> checkHub(@PathVariable("hubId") UUID hubId) {
        return ResponseEntity.ok(hubService.isExistingHub(hubId));
    }

    @GetMapping("/region")
    public ResponseEntity<GetHubResponse> getHubByRegion(@RequestParam String region) {
        return ResponseEntity.ok(hubService.getHubByRegion(region));
    }

    @GetMapping
    public ResponseEntity<SearchHubResponse> searchHub(
            @RequestHeader("X-User-Email") String email,
            @ModelAttribute SearchHubRequest searchHubRequest
    ) {
        return ResponseEntity.ok(hubService.searchHub(email, searchHubRequest));
    }

    @RoleCheck({"MASTER"})
    @PatchMapping("/{hubId}")
    public ResponseEntity<UpdateHubResponse> updateHub(
            @RequestHeader("X-User-Role") String role,
            @PathVariable("hubId") UUID hubId,
            @RequestBody UpdateHubRequest updateHubRequest) {
        return ResponseEntity.ok(hubService.updateHub(hubId, updateHubRequest));
    }

    @RoleCheck({"MASTER"})
    @PatchMapping("/{hubId}/delete")
    public ResponseEntity<Void> deleteHub(@PathVariable("hubId") UUID hubId) {
        hubService.deleteHub(hubId);

        return ResponseEntity.noContent().build();
    }
}
