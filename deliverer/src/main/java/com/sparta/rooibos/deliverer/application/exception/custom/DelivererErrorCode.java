package com.sparta.rooibos.deliverer.application.exception.custom;

import com.sparta.rooibos.deliverer.application.exception.ErrorCodeProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum DelivererErrorCode implements ErrorCodeProvider {
    DELIVERER_NOT_FOUND(HttpStatus.NOT_FOUND, "DLV-01", "해당 배송자를 찾을 수 없습니다."),
    DUPLICATE_DELIVERER(HttpStatus.CONFLICT, "DLV-02", "이미 등록된 배송자입니다."),
    MAX_DELIVERER_REACHED(HttpStatus.BAD_REQUEST, "DLV-03", "해당 소속에는 최대 10명의 배송자만 등록할 수 있습니다."),
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "DLV-04", "접근 권한이 없습니다."),
    NO_DELIVERER_AVAILABLE(HttpStatus.NOT_FOUND, "DLV-05", "배정 가능한 배송자가 존재하지 않습니다."),
    ALREADY_DELETED_DELIVERER(HttpStatus.BAD_REQUEST, "DLV-06", "이미 삭제된 배송 담당자입니다."),
    ALREADY_NOT_ASSIGNED(HttpStatus.BAD_REQUEST, "DLV-07", "이미 배정되지 않은 상태입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
