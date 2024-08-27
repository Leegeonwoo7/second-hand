package com.secondhandplatform.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailCheckResponseDto {

    private boolean isExist;
    private String message;
    private String email;
}
