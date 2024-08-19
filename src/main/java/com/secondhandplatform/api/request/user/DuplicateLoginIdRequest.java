package com.secondhandplatform.api.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DuplicateLoginIdRequest {

    @NotBlank
    private String loginId;

    @Builder
    private DuplicateLoginIdRequest(String loginId) {
        this.loginId = loginId;
    }
}
