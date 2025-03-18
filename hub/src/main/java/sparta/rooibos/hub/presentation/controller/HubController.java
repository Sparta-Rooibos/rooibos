package sparta.rooibos.hub.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.rooibos.hub.application.dto.request.CreateHubRequestDto;
import sparta.rooibos.hub.application.dto.request.UpdateHubRequestDto;
import sparta.rooibos.hub.application.dto.response.CreateHubResponseDto;
import sparta.rooibos.hub.application.dto.response.GetHubResponseDto;
import sparta.rooibos.hub.application.dto.response.SearchHubResponseDto;
import sparta.rooibos.hub.application.dto.response.UpdateHubResponseDto;
import sparta.rooibos.hub.application.service.port.HubService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hub")
public class HubController {

    private final HubService hubService;

    @PostMapping
    public ResponseEntity<CreateHubResponseDto> createHub(@RequestBody CreateHubRequestDto createHubRequestDto) {
        return ResponseEntity.ok(hubService.createHub(createHubRequestDto));
    }

    @GetMapping("/{hubId}")
    public ResponseEntity<GetHubResponseDto> getHub(@PathVariable UUID hubId) {
        return ResponseEntity.ok(hubService.getHub(hubId));
    }

    @GetMapping
    public ResponseEntity<SearchHubResponseDto> searchHub(
            // TODO @ModelAttribute 로 리팩토링
            // TODO 기본 값 넣어야 함
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String region,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(hubService.searchHub(name, region, page, size));
    }

    // TODO 멱등성 고려하면 PUT 쓰는 게 낫지 않나?
    @PatchMapping("/{hubId}")
    public ResponseEntity<UpdateHubResponseDto> updateHub(@RequestParam UpdateHubRequestDto updateHubRequestDto) {
        return ResponseEntity.ok(hubService.updateHub(updateHubRequestDto));
    }

    @PatchMapping("/{hubId}/delete")
    public ResponseEntity<Void> deleteHub(@PathVariable UUID hubId) {
        hubService.deleteHub(hubId);

        return ResponseEntity.noContent().build();
    }
}
