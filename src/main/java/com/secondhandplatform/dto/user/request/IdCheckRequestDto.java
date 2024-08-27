package com.secondhandplatform.dto.user.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IdCheckRequestDto {

    private String loginId;
}
