package com.sparta.rooibos.client.presentation.dto.req;


import com.sparta.rooibos.client.application.dto.req.SearchClientApplicationRequest;

public record SearchClientRequest(String name, String address, String type, String sort, int page, int size) {


    public SearchClientApplicationRequest toApplication() {
        return new SearchClientApplicationRequest(name, address, type, sort, page, size);
    }


}
