package com.secondhandplatform.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationCodeResponseDto {

    private boolean isSuccess;
    private String message;
    private String email;
    private String loginId;
}
