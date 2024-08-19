package com.secondhandplatform.api.request.user;

import lombok.Getter;

@Getter
public class CheckCertificationRequest {

    private String email;
    private String certificationNumber;

    public CheckCertificationRequest(String email, String certificationNumber) {
        this.email = email;
        this.certificationNumber = certificationNumber;
    }
}
