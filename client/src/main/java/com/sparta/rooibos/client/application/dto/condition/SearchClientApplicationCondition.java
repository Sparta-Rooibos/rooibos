package com.sparta.rooibos.client.application.dto.condition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@Getter
public final class SearchClientApplicationCondition {
    private final Pageable pageable;
    private final String name;
    private final String address;
    private final String type;
    private final Boolean deleteCheck;

}
