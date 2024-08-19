package com.secondhandplatform.service.user.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EmailCertificationResponse {

    private boolean success;

    @Builder
    public EmailCertificationResponse(boolean success) {
        this.success = success;
    }


}
