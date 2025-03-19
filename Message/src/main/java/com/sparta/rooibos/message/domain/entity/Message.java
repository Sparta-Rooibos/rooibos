package com.sparta.rooibos.message.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
public class Message extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID id;
    private String sender;
    private String recipient;
    private String text;
    private String slackAccount;
    private Boolean status;
    private LocalDateTime sendingAt;

    public static Message create(String recipient, String content) {
        return new Message("발송 아이디", recipient, content, null, false, null);
    }


    public Message(String sender, String recipient, String text, String slackAccount, Boolean status, LocalDateTime sendingAt) {
        this.sender = sender;
        this.recipient = recipient;
        this.text = text;
        this.slackAccount = slackAccount;
        this.status = status;
        this.sendingAt = sendingAt;
    }

    public void changeMessage(String massage) {
        this.text = massage;
    }
}
