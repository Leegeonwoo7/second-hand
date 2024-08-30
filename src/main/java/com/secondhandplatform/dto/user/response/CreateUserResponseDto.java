package com.secondhandplatform.dto.user.response;

import com.secondhandplatform.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponseDto {

    private Long id;
    private String message;


}
