package com.sparta.rooibos.auth.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Refresh {
    @Id
    @GeneratedValue
    @Column(name = "refresh_id", columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String refresh;

    @Column(nullable = false)
    private Date expiration;

    public static Refresh create(String email, String refreshToken, Long expiredMs) {
        Date expiration = new Date(System.currentTimeMillis() + expiredMs);
        return new Refresh(null, email, refreshToken, expiration);
    }
}
