package com.sparta.rooibos.user.application.dto.response;

import com.sparta.rooibos.user.domain.entity.User;

public record CachedUserResponse(
        String username,
        String email,
        String password,
        String role
) {
    public static CachedUserResponse from(User user) {
        return new CachedUserResponse(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().name()
        );
    }
}
