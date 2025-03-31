package com.sparta.rooibos.user.infrastructure.auditing;

import java.util.Optional;

public class UserAuditorContext {
    private static final ThreadLocal<String> emailHolder = new ThreadLocal<>();
    private static final ThreadLocal<String> roleHolder = new ThreadLocal<>();

    public static void set(String email, String role) {
        emailHolder.set(email);
        roleHolder.set(role);
    }

    public static String getEmail() {
        return Optional.ofNullable(emailHolder.get()).orElse("anonymous");
    }

    public static String getRole() {
        return Optional.ofNullable(roleHolder.get()).orElse("UNKNOWN");
    }

    public static void clear() {
        emailHolder.remove();
        roleHolder.remove();
    }
}

