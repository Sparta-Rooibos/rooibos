package com.sparta.rooibos.user.domain.critria;

public record UserSearchCriteria(
        String keyword,
        String sort,
        String filter,
        int page,
        int size
) {
}
