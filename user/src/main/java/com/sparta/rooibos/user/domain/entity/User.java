package com.sparta.rooibos.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "p_users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "user_id", columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "slack_account", nullable = false, unique = true)
    private String slackAccount;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public static User create(String username, String email, String password, String slackAccount, String phone, Role role) {
        return new User(null, username, email, password, slackAccount, phone, role);
    }

    public void updateUser(String email, String slackAccount, String password, String phone, Role role) {
        this.email = email;
        this.slackAccount = slackAccount;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

//    public void delete(String deletedBy) {
//        this.hidden = true;
//        markAsDeleted(deletedBy);
//    }
}