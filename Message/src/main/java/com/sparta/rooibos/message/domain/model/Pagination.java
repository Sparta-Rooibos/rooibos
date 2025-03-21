package com.sparta.rooibos.message.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Pagination<T> {
    private final int page;
    private final int size;
    private final Long total;
    private final List<T> content;

    public static <T> Pagination<T> of(int page, int size, Long total, List<T> content) {
        return new Pagination<>(page, size, total, content);
    }
}
