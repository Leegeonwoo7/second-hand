package com.secondhandplatform.service.user.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SendCertificationResponse {

    private boolean isSuccess;

    @Builder
    private SendCertificationResponse(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public static SendCertificationResponse success() {
        return SendCertificationResponse
                .builder()
                .isSuccess(true)
                .build();
    }

    public static SendCertificationResponse fail() {
        return SendCertificationResponse
                .builder()
                .isSuccess(false)
                .build();
    }
}
