package com.secondhandplatform.dto.user.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IdCheckResponseDto {

    private boolean isDuplicate;
    private String message;
    private String loginId;
}
