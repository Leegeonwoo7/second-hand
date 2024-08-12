package com.secondhandplatform.api.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EmailCertificationRequest {

    @NotBlank
    private String loginId;

    @NotBlank
    private String email;

    @Builder
    private EmailCertificationRequest(String loginId, String email) {
        this.loginId = loginId;
        this.email = email;
    }
}
