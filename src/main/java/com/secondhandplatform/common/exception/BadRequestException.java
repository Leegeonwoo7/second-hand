package com.secondhandplatform.common.exception;

public class BadRequestException extends RuntimeException{


    public static final String WRONG_CERTIFICATION_CODE = "일치하지 않는 인증번호";
    public static final String WRONG_EMAIL = "이메일이 일치하지 않습니다.";
    public static final String WRONG_LOGIN_INFO = "아이디 혹은 비밀번호가 잘못되었습니다.";

    public static final String NOT_EXIST_USER = "존재하지 않는 회원";
    public static final String NOT_EXIST_PRODUCT = "존재하지 않는 상품";

    public static final String LOGIN_FIRST = "상품을 등록하기위한 회원이 식별되지 않습니다.";


    public BadRequestException(String message) {
        super(message);
    }
}
