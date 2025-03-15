package com.sparta.rooibos.client.application.dto.condition;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
public class SearchClientApplicationCondition {
    private String name;
    private String address;
    private String type;
    private Pageable pageable;


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public Pageable getPageable() {
        return pageable;
    }
}
