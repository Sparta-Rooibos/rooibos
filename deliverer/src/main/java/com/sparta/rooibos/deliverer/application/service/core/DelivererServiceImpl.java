package com.sparta.rooibos.deliverer.application.service.core;

import com.sparta.rooibos.deliverer.application.dto.request.DelivererRequest;
import com.sparta.rooibos.deliverer.application.dto.request.DelivererSearchRequest;
import com.sparta.rooibos.deliverer.application.dto.response.DelivererListResponse;
import com.sparta.rooibos.deliverer.application.dto.response.DelivererResponse;
import com.sparta.rooibos.deliverer.application.exception.BusinessDelivererException;
import com.sparta.rooibos.deliverer.application.exception.custom.DelivererErrorCode;
import com.sparta.rooibos.deliverer.application.service.port.DelivererService;
import com.sparta.rooibos.deliverer.domain.entity.Deliverer;
import com.sparta.rooibos.deliverer.domain.entity.DelivererStatus;
import com.sparta.rooibos.deliverer.domain.entity.DelivererType;
import com.sparta.rooibos.deliverer.domain.model.Pagination;
import com.sparta.rooibos.deliverer.domain.repository.DelivererRepository;
import com.sparta.rooibos.deliverer.infrastructure.auditing.UserAuditorContext;
import com.sparta.rooibos.deliverer.infrastructure.feign.HubManagerClient;
import com.sparta.rooibos.deliverer.infrastructure.feign.UserClient;
import com.sparta.rooibos.deliverer.infrastructure.feign.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DelivererServiceImpl implements DelivererService {
    private final DelivererRepository delivererRepository;
    private final UserClient userClient;
    private final HubManagerClient hubManagerClient;

    @Transactional
    public DelivererResponse createDeliverer(DelivererRequest request) {
        if (delivererRepository.existsById(request.userId())) {
            throw new BusinessDelivererException(DelivererErrorCode.DUPLICATE_DELIVERER);
        }

        UUID hubId;
        if ("ROLE_HUB".equals(UserAuditorContext.getRole())) {
            hubId = hubManagerClient.getHubIdByEmail(UserAuditorContext.getEmail());
        } else {
            hubId = request.hubId();
        }

        int count = delivererRepository.countByHubIdAndTypeAndHiddenFalse(hubId, request.type());
        if (count >= 10) {
            throw new BusinessDelivererException(DelivererErrorCode.MAX_DELIVERER_REACHED);
        }

        int maxOrder = delivererRepository.findMaxOrderByHubId(hubId);
        int nextOrder = (maxOrder + 1) % 10;

        UserResponse user = userClient.getUserByMaster(request.userId());

        Deliverer deliverer = Deliverer.create(
                user.id(),
                user.username(),
                user.email(),
                user.slackAccount(),
                user.phone(),
                request.type(),
                hubId,
                nextOrder
        );

        delivererRepository.save(deliverer);

        return DelivererResponse.from(deliverer);
    }

    @Transactional
    public DelivererResponse updateDeliverer(UUID delivererId, DelivererRequest request) {
        Deliverer deliverer = delivererRepository.findById(delivererId)
                .orElseThrow(() -> new BusinessDelivererException(DelivererErrorCode.DELIVERER_NOT_FOUND));


        int count = delivererRepository.countByHubIdAndHiddenFalse(request.hubId());
        if (count >= 10) {
            throw new BusinessDelivererException(DelivererErrorCode.MAX_DELIVERER_REACHED);
        }

        if ("ROLE_HUB".equalsIgnoreCase(UserAuditorContext.getRole())) {
            UUID myHubId = hubManagerClient.getHubIdByEmail(UserAuditorContext.getEmail());
            if (!deliverer.getHubId().equals(myHubId)) {
                throw new BusinessDelivererException(DelivererErrorCode.FORBIDDEN_ACCESS);
            }
        }

        deliverer.update(
                request.type(),
                request.hubId()
        );

        delivererRepository.save(deliverer);
        return DelivererResponse.from(deliverer);
    }

    @Transactional
    public void deleteDeliverer(UUID delivererId) {
        Deliverer deliverer = delivererRepository.findById(delivererId)
                .orElseThrow(() -> new BusinessDelivererException(DelivererErrorCode.DELIVERER_NOT_FOUND));

        if ("ROLE_HUB".equalsIgnoreCase(UserAuditorContext.getRole())) {
            UUID myHubId = hubManagerClient.getHubIdByEmail(UserAuditorContext.getEmail());
            if (!deliverer.getHubId().equals(myHubId)) {
                throw new BusinessDelivererException(DelivererErrorCode.FORBIDDEN_ACCESS);
            }
        }

        String email = UserAuditorContext.getEmail();
        deliverer.delete(email);
        delivererRepository.save(deliverer);
    }

    @Transactional(readOnly = true)
    public DelivererResponse getDeliverer(UUID delivererId) {
        Deliverer deliverer = delivererRepository.findById(delivererId)
                .orElseThrow(() -> new BusinessDelivererException(DelivererErrorCode.DELIVERER_NOT_FOUND));
        switch (UserAuditorContext.getRole()) {
            case "ROLE_HUB" -> {
                UUID myHubId = hubManagerClient.getHubIdByEmail(UserAuditorContext.getEmail());
                if (!deliverer.getHubId().equals(myHubId)) {
                    throw new BusinessDelivererException(DelivererErrorCode.FORBIDDEN_ACCESS);
                }
            }
            case "ROLE_DELIVERER" -> {
                if (!deliverer.getEmail().equals(UserAuditorContext.getEmail())) {
                    throw new BusinessDelivererException(DelivererErrorCode.FORBIDDEN_ACCESS);
                }
            }
        }
        return DelivererResponse.from(deliverer);
    }

    @Transactional(readOnly = true)
    public DelivererListResponse searchDeliverers(DelivererSearchRequest request) {
        Pagination<Deliverer> resultPage = delivererRepository.searchDeliverers(request);
        return DelivererListResponse.from(resultPage);
    }

    @Transactional(readOnly = true)
    public DelivererResponse assignNextDeliverer(UUID hubId, DelivererType type) {
        Deliverer deliverer = delivererRepository.findNextAvailableDeliverer(hubId, type)
                .orElseThrow(() -> new BusinessDelivererException(DelivererErrorCode.NO_DELIVERER_AVAILABLE));

        if (deliverer.isHidden()) {
            throw new BusinessDelivererException(DelivererErrorCode.NO_DELIVERER_AVAILABLE);
        }

        deliverer.assign();
        delivererRepository.save(deliverer);

        return DelivererResponse.from(deliverer);
    }

    @Transactional
    public void cancelAssignment(UUID delivererId) {
        Deliverer deliverer = delivererRepository.findById(delivererId)
                .orElseThrow(() -> new BusinessDelivererException(DelivererErrorCode.DELIVERER_NOT_FOUND));

        if (deliverer.isHidden()) {
            throw new BusinessDelivererException(DelivererErrorCode.ALREADY_DELETED_DELIVERER);
        }

        if (deliverer.getStatus() == DelivererStatus.UNASSIGNED) {
            throw new BusinessDelivererException(DelivererErrorCode.ALREADY_NOT_ASSIGNED);
        }

        deliverer.unassign();
        delivererRepository.save(deliverer);
        return DelivererResponse.from(deliverer);
    }
}
