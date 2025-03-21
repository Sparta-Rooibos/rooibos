package com.sparta.rooibos.message.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Message extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID id;
    private String sender;
    private String recipient;

    @Column(columnDefinition = "TEXT")
    private String text;
    private Boolean status;
    private LocalDateTime sendingAt;

    public static Message create(String content, String sender, String recipient) {
        return new Message(content, recipient, sender, true);
    }


    public Message(String text, String recipient, String sender, Boolean status) {
        this.text = text;
        this.sender = sender;
        this.recipient = recipient;
        this.status = status;
        this.sendingAt = LocalDateTime.now();
        this.createBy = "계정아이디";
    }

    public void changeMessage(String massage) {
        this.text = massage;
    }
}
