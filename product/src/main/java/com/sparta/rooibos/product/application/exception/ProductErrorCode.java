package com.sparta.rooibos.product.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode {
    // 공통
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"COM-01", "내부 서버 에러"),
    JSON_PARSING_ERROR(HttpStatus.BAD_REQUEST,"COM-02","JSON 파싱 에러"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND,"COM-03","리소스를 찾을 수 없습니다."),
    INVALID_CODE(HttpStatus.BAD_REQUEST,"COM-04","잘못된 값을 입력하였습니다."),
    SQL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"COM-05","SQL 에러"),
    // 클라이언트(업체)
    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "PRD-01", "제공하는 상품이 존재하지 않습니다."),
    NOT_EXITS_PRODUCT(HttpStatus.BAD_REQUEST, "PRD-02", "이미 존재하는 상품입니다."),
    NOT_SUPPORTED_TYPE(HttpStatus.BAD_REQUEST, "PRD-03", "수령업체는 제품을 생산할 수 없습니다."),
    NOT_MANAGE_CLIENT(HttpStatus.FORBIDDEN, "PRD-04", "해당 업체의 담당자가 아닙니다."),
    NOT_FOUND_CLIENT(HttpStatus.NOT_FOUND, "RPD-05" , "해당 업체를 찾을 수 없습니다." );
    private final HttpStatus status;
    private final String code;
    private final String message;
}
