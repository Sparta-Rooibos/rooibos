package com.sparta.rooibos.user.application.service.port;

import java.util.UUID;

public interface UserApproveService {
    void approveUser(UUID userId);
    void rejectUser(UUID userId);
}
