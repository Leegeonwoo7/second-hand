package com.secondhandplatform.user.dto.response;

import com.secondhandplatform.user.domain.SignupType;
import com.secondhandplatform.user.domain.User;
import com.secondhandplatform.user.domain.UserType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class JoinResponse {

    private Long userId;
    //TODO 로그인아이디, 비밀번호는 추후 삭제
    private String username;
    private String password;
    private String email;
    private String phone;
    private LocalDate birthday;
    private String name;
    private UserType userType;
    private SignupType signupType;

    public static JoinResponse from(User entity) {
        return JoinResponse.builder()
                .userId(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .birthday(entity.getBirthday())
                .name(entity.getName())
                .userType(entity.getUserType())
                .signupType(entity.getSignupType())
                .build();
    }
}
