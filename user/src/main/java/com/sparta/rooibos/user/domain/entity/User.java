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
public class User extends BaseEntity {
    @Id
    @GeneratedValue
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

    private boolean hidden;

    @Enumerated(EnumType.STRING)
    private UserRoleStatus status;

    public static User create(String username, String email, String password, String slackAccount, String phone, Role role) {
        return new User(null, username, email, password, slackAccount, phone, role, false, UserRoleStatus.PENDING);
    }

    public static User createByMaster(String username, String email, String password, String slackAccount, String phone, Role role, UserRoleStatus status) {
        return new User(null, username, email, password, slackAccount, phone, role, false, status);
    }

    public void update(String slackAccount, String password, String phone) {
        if (slackAccount != null) this.slackAccount = slackAccount;
        if (password != null) this.password = password;
        if (phone != null) this.phone = phone;
    }

    public void delete(String deletedBy) {
        this.hidden = true;
        this.markAsDeleted(deletedBy);
    }

    public void approve() {
        this.status = UserRoleStatus.ACTIVE;
    }

    public void reject() {
        this.status = UserRoleStatus.REJECTED;
    }
}