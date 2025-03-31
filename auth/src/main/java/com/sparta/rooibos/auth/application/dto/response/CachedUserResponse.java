package com.sparta.rooibos.auth.application.dto.response;

import java.io.Serializable;

public record CachedUserResponse (
        String username,
        String email,
        String password,
        String role,
        String stauts
)implements Serializable{}
