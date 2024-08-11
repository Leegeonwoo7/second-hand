package com.secondhandplatform.service.user.response;

import lombok.Getter;

@Getter
public class IdCheckResponse {

    private final static String SUCCESS = "SUCCESS";
    private final static String FAIL = "DUPLICATE_ID";

    private String result;
    private String loginId;

    private IdCheckResponse(String result, String loginId) {
        this.result = result;
        this.loginId = loginId;
    }

    public static IdCheckResponse success(String loginId) {
        return new IdCheckResponse(SUCCESS, loginId);
    }

    public static IdCheckResponse duplicate(String loginId) {
        return new IdCheckResponse(FAIL, loginId);
    }
}
