package com.sparta.rooibos.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
public class UserStreamEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String eventType;
    //    private String username;
//    private String email;
//    private String password;
//    private Role role;
    private final Object payload;

    public UserStreamEvent(String eventType, Object payload) {
        this.eventType = eventType;
        this.payload = payload;
    }
}

