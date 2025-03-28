package com.sparta.rooibos.message.infrastuct.slack;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.sparta.rooibos.message.application.slack.SlackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class SlackServiceImpl implements SlackService {
    @Value("${slack.token}")
    private String slackToken;

    @Override
    public void sendMessage(String content) throws SlackApiException, IOException {

        Slack slack = Slack.getInstance();

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("#테스트입니다")  // 채널 정보
                .text(content)  // 메시지 내용
                .build();

        ChatPostMessageResponse response = slack.methods(slackToken).chatPostMessage(request);

        if (response.isOk()) {
            log.info("Message sent successfully");
        } else {
            log.error("Message sent failed");
        }

    }
}
