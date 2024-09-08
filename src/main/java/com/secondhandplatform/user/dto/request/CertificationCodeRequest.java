package com.secondhandplatform.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CertificationCodeRequest {

    private String email;
    private String username;
}
