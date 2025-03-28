package com.sparta.rooibos.auth.application.service.port;

import com.sparta.rooibos.auth.application.dto.request.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    void login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response);
    void logout(HttpServletRequest request, HttpServletResponse response);
    void reissueToken(HttpServletRequest request, HttpServletResponse response);
    void banUser(String email);
}
