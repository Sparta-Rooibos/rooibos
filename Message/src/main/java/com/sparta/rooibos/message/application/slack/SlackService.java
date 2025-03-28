package com.sparta.rooibos.message.application.slack;

import com.slack.api.methods.SlackApiException;

import java.io.IOException;

public interface SlackService {
    void sendMessage(String content) throws SlackApiException, IOException;
}
