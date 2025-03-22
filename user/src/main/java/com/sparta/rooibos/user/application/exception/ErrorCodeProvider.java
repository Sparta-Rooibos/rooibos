package com.sparta.rooibos.user.application.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCodeProvider {
    String getCode();
    String getMessage();
    HttpStatus getStatus();
}
