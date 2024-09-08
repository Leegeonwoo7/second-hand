package com.secondhandplatform.common.exception;

public class DuplicateException extends RuntimeException{

    public static final String EMAIL_DUPLICATE = "이미 존재하는 이메일 입니다.";
    public static final String USERNAME_DUPLICATE = "이미 존재하는 아이디입니다.";

    public DuplicateException(String message) {
        super(message);
    }
}
