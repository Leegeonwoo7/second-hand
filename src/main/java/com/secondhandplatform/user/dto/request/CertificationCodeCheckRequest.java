package com.secondhandplatform.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CertificationCodeCheckRequest {

    private String email;
    private String certificationCode;
}
