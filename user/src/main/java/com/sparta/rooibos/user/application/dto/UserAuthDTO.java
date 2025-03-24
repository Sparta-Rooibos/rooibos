package com.sparta.rooibos.user.application.dto;

import com.sparta.rooibos.user.domain.entity.Role;
import com.sparta.rooibos.user.domain.entity.User;

public record UserAuthDTO (
        String username,
        String email,
        String password,
        Role role
){
    public static UserAuthDTO fromEntity(User user) {
        return new UserAuthDTO(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }
}