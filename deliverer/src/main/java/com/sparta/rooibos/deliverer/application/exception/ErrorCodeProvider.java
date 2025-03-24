package com.sparta.rooibos.deliverer.application.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCodeProvider {
    String getCode();
    String getMessage();
    HttpStatus getStatus();
}
