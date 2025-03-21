package com.sparta.rooibos.user.application.service;

import com.sparta.rooibos.user.application.dto.UserAuthDTO;

public interface EventProvider {
    void sendUserInfo(UserAuthDTO dto);
    void sendUserDeleteInfo(String email);
    void sendUserReportInfo(String email);
}