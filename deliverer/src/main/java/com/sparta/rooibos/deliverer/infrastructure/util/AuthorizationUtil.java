package com.sparta.rooibos.deliverer.infrastructure.util;

import com.querydsl.core.BooleanBuilder;
import com.sparta.rooibos.deliverer.domain.entity.QDeliverer;
import com.sparta.rooibos.deliverer.infrastructure.auditing.UserAuditorContext;
import com.sparta.rooibos.deliverer.infrastructure.feign.HubManagerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthorizationUtil {
    private final HubManagerClient hubManagerClient;
    public BooleanBuilder getPermissionWhereClause(QDeliverer deliverer) {
        BooleanBuilder builder = new BooleanBuilder();
        String role = UserAuditorContext.getRole();
        String email = UserAuditorContext.getEmail();

        switch (role) {
            case "MASTER" -> {
            }
            case "HUB" -> {
                UUID hubId = hubManagerClient.getHubIdByEmail(email);
                builder.and(deliverer.hubId.eq(hubId));
            }
            case "DELIVERER" -> builder.and(deliverer.email.eq(email));
            default -> throw new IllegalArgumentException("유효하지 않은 권한입니다.");
        }

        return builder;
    }
}
