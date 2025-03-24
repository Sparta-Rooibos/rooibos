<<<<<<<< HEAD:delivery/src/main/java/com/sparta/rooibus/delivery/application/exception/custom/DeliveryError.java
package com.sparta.rooibus.delivery.application.exception.custom;
========
package sparta.rooibos.hub.application.exception.custom;
>>>>>>>> develop:hub/src/main/java/sparta/rooibos/hub/application/exception/custom/HubErrorCode.java

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DeliveryError {
    DELIVERY_NOT_FOUND(HttpStatus.NO_CONTENT, "DNF-01", "해당 배송을 찾을 수 없습니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;
}
