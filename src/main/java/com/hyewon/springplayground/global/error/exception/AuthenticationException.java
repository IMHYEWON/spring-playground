package com.hyewon.springplayground.global.error.exception;


import com.hyewon.springplayground.global.error.ErrorCode;

public class AuthenticationException extends RuntimeException {
    private ErrorCode errorCode;

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
