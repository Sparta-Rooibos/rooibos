package com.sparta.rooibos.user.application.dto.response;

import com.sparta.rooibos.user.domain.model.Pagination;
import com.sparta.rooibos.user.domain.entity.User;

import java.util.List;
import java.util.UUID;

public record UserListResponse(
        int page,
        int size,
        long totalElements,
        long totalPages,
        List<UserSummary> contents
) {
    public static UserListResponse from(Pagination<User> pagination) {
        int size = pagination.getSize();
        return new UserListResponse(
                pagination.getPage(),
                size,
                pagination.getTotal(),
                (pagination.getTotal() + size - 1) / size, // totalPages 계산
                pagination.getContent().stream()
                        .map(UserSummary::from)
                        .toList()
        );
    }

    public record UserSummary(
            UUID id,
            String username,
            String email,
            String slackAccount,
            String phone,
            com.sparta.rooibos.user.domain.entity.Role role
    ) {
        public static UserSummary from(User user) {
            return new UserSummary(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getSlackAccount(),
                    user.getPhone(),
                    user.getRole()
            );
        }
    }
}
