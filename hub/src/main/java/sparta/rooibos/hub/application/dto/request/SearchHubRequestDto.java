package sparta.rooibos.hub.application.dto.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public record SearchHubRequestDto(
        Optional<String> name,
        Optional<String> region,
        Optional<String> latitude,
        Optional<String> longitude,
        int page,
        int size
) {

    public Pageable toPageable() {
        return PageRequest.of(page, size);
    }
}
