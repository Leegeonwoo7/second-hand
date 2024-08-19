package com.secondhandplatform.api.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailCertificationRequest {

    @NotBlank
    private String email;

    public EmailCertificationRequest(String email) {
        this.email = email;
    }
}
