package com.secondhandplatform.api.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SendCertificationRequest {

    @Email
    @NotBlank
    private String email;

    @Builder
    private SendCertificationRequest(String email) {
        this.email = email;
    }


}
