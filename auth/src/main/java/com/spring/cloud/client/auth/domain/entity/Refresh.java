package com.spring.cloud.client.auth.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Refresh {
    @Id
    @Column(name = "refresh_id", columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String refresh;

    @Column(nullable = false)
    private Date expiration;

    public static Refresh create(String username, String refreshToken, Long expiredMs) {
        Date expiration = new Date(System.currentTimeMillis() + expiredMs);
        return new Refresh(null, username, refreshToken, expiration);
    }
}
