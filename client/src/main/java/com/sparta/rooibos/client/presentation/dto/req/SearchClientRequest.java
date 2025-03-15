package com.sparta.rooibos.client.presentation.dto.req;


import com.sparta.rooibos.client.application.dto.req.SearchClientApplicationRequest;
import org.springframework.data.domain.Pageable;

public record SearchClientRequest(String name, String address, String type,
                                  String sort, Integer page, Integer size,
                                  Boolean isDeleted) {

    public SearchClientRequest {
        sort = sort == null ? "createAt" : sort;
        page = page == null ? 1 : page;
        size = size == null ? 10 : size;
    }

    public SearchClientApplicationRequest toApplication(Pageable pageable) {
        return new SearchClientApplicationRequest(name, address, type, isDeleted, pageable);
    }


}
