package sparta.rooibos.hub.application.dto.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public record SearchHubRequestDto(
        String name,
        String region,
        int page,
        int size
) {

    public Pageable toPageable() {
        return PageRequest.of(page, size);
    }
}
