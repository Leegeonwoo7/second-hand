package com.secondhandplatform.service.user.response;

import lombok.Getter;

@Getter
public class CheckCertificationResponse {

    private boolean success;

    private CheckCertificationResponse(boolean success) {
        this.success = success;
    }

    public static CheckCertificationResponse success() {
        return new CheckCertificationResponse(true);
    }

    public static CheckCertificationResponse fail() {
        return new CheckCertificationResponse(false);
    }

    public boolean isSuccess() {
        return success;
    }
}
