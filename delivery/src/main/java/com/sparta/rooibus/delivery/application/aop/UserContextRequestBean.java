package com.sparta.rooibus.delivery.application.aop;

import java.util.UUID;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Getter
@Component
@Scope(value = "request",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserContextRequestBean {

    private String email;
    private String role;
    private String name;
    private UUID userId;

    public void set(String role, String email, String name, UUID userId) {
        this.role = role;
        this.email = email;
        this.name = name;
        this.userId = userId;
    }
}
