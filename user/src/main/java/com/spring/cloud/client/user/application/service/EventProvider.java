package com.spring.cloud.client.user.application.service;

import com.spring.cloud.client.user.application.dto.UserAuthDTO;

public interface EventProvider {
    void sendUserInfo(UserAuthDTO userAuthDTO);
    void sendUserDeleteInfo(String email);
    void sendUserReportInfo(String email);
}