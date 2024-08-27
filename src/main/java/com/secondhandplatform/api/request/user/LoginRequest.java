package com.secondhandplatform.api.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    @Builder
    private LoginRequest(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
