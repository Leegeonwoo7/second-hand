package com.secondhandplatform.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Response {

    public static final String USERNAME_OK = "사용가능한 아이디입니다.";
    public static final String EMAIL_OK = "사용가능한 이메일입니다.";
    public static final String EMAIL_SEND_OK = "이메일 전송완료";
    public static final String CERTIFICATION_CHECK_OK = "이메일 인증 완료";

    private String message;

}
