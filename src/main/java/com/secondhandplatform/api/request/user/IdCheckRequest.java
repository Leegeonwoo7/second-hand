package com.secondhandplatform.api.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IdCheckRequest {

    @NotBlank
    private String loginId;

    @Builder
    private IdCheckRequest(String loginId) {
        this.loginId = loginId;
    }
}
