package com.sparta.rooibos.client.application.dto.req;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public record SearchClientApplicationRequest(String name, String address, String type, String sort,
                                             int page,
                                             int size) {


    public Pageable getPageable() {
        return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort));
    }
}
