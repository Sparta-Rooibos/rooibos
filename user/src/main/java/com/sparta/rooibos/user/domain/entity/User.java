package com.sparta.rooibos.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
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

    public void updateUser(String email, String slackAccount, String password, String phone, Role role) {
        this.email = email;
        this.slackAccount = slackAccount;
        this.password = password;
        this.phone = phone;
        this.role = role;
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