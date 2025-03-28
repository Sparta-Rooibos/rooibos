package com.sparta.rooibos.user.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    MASTER("ROLE_MASTER"),      // 최상위 관리자
    HUB("ROLE_HUB"),            // 허브 관리자
    DELIVERY("ROLE_DELIVERY"),  // 배송 관리자
    CLIENT("ROLE_CLIENT");

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

//    @JsonValue
//    public String toValue() {
//        return name(); // or authority if you'd rather serialize "ROLE_MASTER"
//    }
//
//    @JsonCreator
//    public static Role from(String value) {
//        return Role.valueOf(value.toUpperCase());
//    }
}



