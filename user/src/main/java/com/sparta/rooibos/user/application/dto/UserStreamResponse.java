package com.sparta.rooibos.user.application.dto;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sparta.rooibos.user.domain.entity.Role;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)

public record UserStreamResponse(
        String eventType,
        String email,
        String username,
        String password,
        Role role
) {
    public static UserStreamResponse from(UserStreamRequest request) {
        return new UserStreamResponse("create", request.email(), request.username(), request.password(), request.role());
    }

    public static UserStreamResponse delete(String email) {
        return new UserStreamResponse("delete", email, null, null, null);
    }

    public static UserStreamResponse report(String email) {
        return new UserStreamResponse("report", email, null, null, null);
    }

}



