package com.sparta.rooibos.user.infrastructure.kafka;


import com.sparta.rooibos.user.application.dto.UserAuthDTO;
import com.sparta.rooibos.user.application.service.EventProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEventProducer implements EventProvider {
    private final KafkaTemplate<String, UserAuthDTO> authTemplate;
    private final KafkaTemplate<String, String> emailTemplate;

    public void sendUserInfo(UserAuthDTO userAuthDTO) {
        log.info("유저 정보 응답 전송: {}", userAuthDTO.email());
        authTemplate.send("auth-service.user.synced", userAuthDTO);
    }

    public void sendUserDeleteInfo(String email) {
        log.info("유저 삭제 이벤트 전송: {}", email);
        emailTemplate.send("auth-service.user.deleted", email);
    }

    public void sendUserReportInfo(String email) {
        log.info("🚨 유저 신고 이벤트 전송: {}", email);
        emailTemplate.send("auth-service.user.reported", email);
    }
}