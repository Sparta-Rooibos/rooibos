package com.sparta.rooibos.user.application.dto;

import com.sparta.rooibos.user.domain.entity.Role;
import com.sparta.rooibos.user.domain.entity.User;

public record UserStreamRequest(
        String username,
        String email,
        String password,
        Role role
){
    public static UserStreamRequest fromEntity(User user) {
        return new UserStreamRequest(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }
}