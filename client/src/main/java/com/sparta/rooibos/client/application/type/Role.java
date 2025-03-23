package com.sparta.rooibos.client.application.type;

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
    }// 업체 관리자
}
