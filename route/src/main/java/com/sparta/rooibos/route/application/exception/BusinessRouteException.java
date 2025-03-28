package com.sparta.rooibos.route.application.exception;

import com.sparta.rooibos.route.application.exception.custom.RouteErrorCode;
import org.springframework.http.HttpStatus;

public class BusinessRouteException extends RuntimeException {

    private final RouteErrorCode routeErrorCode;

    public BusinessRouteException(RouteErrorCode routeErrorCode) {
        super(routeErrorCode.getMessage());
        this.routeErrorCode = routeErrorCode;
    }

    public HttpStatus getStatus() {
        return routeErrorCode.getStatus();
    }

    public String getErrorCode() {
        return routeErrorCode.getCode();
    }

    public String getMessage() {
        return routeErrorCode.getMessage();
    }
}
