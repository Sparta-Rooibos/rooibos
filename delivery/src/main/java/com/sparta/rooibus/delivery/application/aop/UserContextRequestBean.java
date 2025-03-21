package com.sparta.rooibus.delivery.application.aop;

import java.util.UUID;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserContextRequestBean {

    private UUID userId;
    private String role;

    public void set(String role, UUID userId) {
        this.role = role;
        this.userId = userId;
    }
    public String getRole() {
        return role;
    }

    public UUID getUserId() {
        return userId;
    }
}
