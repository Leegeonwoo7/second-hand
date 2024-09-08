package com.secondhandplatform.common.exception;

public class BadRequestException extends RuntimeException{

    public static final String WRONG_CERTIFICATION_CODE = "일치하지 않는 인증번호";

    public BadRequestException(String message) {
        super(message);
    }
}
