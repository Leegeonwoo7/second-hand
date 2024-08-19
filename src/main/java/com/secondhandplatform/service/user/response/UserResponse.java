package com.secondhandplatform.service.user.response;

import com.secondhandplatform.domain.user.SignupType;
import com.secondhandplatform.domain.user.User;
import com.secondhandplatform.domain.user.UserType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserResponse {

    private String loginId;
    private String password;
    private String email;
    private String phone;
    private LocalDate birthday;
    private String name;
    private UserType userType;
    private SignupType signupType;

    @Builder
    private UserResponse(String loginId, String password, String email, String phone, LocalDate birthday, String name, UserType userType, SignupType signupType) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.name = name;
        this.userType = userType;
        this.signupType = signupType;
    }

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .loginId(user.getLoginId())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .name(user.getName())
                .phone(user.getPhone())
                .signupType(user.getSignupType())
                .userType(user.getUserType())
                .build();
    }
}
