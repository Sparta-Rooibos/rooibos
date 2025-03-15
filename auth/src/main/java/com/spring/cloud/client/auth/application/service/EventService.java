package com.spring.cloud.client.auth.application.service;

import com.spring.cloud.client.auth.application.dto.UserDTO;

public interface EventService {
    UserDTO requestUserInfo(String username);
}
