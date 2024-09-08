package com.secondhandplatform.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Response {

    public static final String USERNAME_OK = "사용가능한 아이디입니다.";

    private String message;

}
