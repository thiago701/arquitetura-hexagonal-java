package com.hexagonal.user_auto.core.domain.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@Slf4j
public class ApiException extends RuntimeException {

    private final Integer statusErrorCode;

    public ApiException(String message, HttpStatus statusErrorCode) {
        super(message);
        this.statusErrorCode = statusErrorCode.value();
    }

}
