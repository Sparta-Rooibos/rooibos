package com.sparta.rooibos.user.application.dto.request;

import com.sparta.rooibos.user.domain.critria.UserSearchCriteria;

public record UserSearchRequest (
     String keyword,
     String sort,
     String filter,
     int page,
     int size
){
    public UserSearchCriteria toCriteria() {
        return new UserSearchCriteria(keyword, sort, filter, page, size);
    }
}