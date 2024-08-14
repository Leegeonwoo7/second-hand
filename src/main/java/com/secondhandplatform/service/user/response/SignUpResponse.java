package com.secondhandplatform.service.user.response;

import com.secondhandplatform.domain.user.SignupType;
import com.secondhandplatform.domain.user.User;
import com.secondhandplatform.domain.user.UserType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignUpResponse {

    private Long id;
    private String loginId;
    private String password;
    private String email;
    private String phone;
    private LocalDate birthday;
    private String name;
    private UserType userType;
    private SignupType signupType;
    private String message;
    private boolean emailVerified;

    @Builder
    private SignUpResponse(String loginId, String password, String email, String phone, LocalDate birthday, String name, UserType userType, SignupType signupType, String message, boolean emailVerified) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.name = name;
        this.userType = userType;
        this.signupType = signupType;
        this.message = message;
        this.emailVerified = emailVerified;
    }

    public static SignUpResponse of(User user) {
        return SignUpResponse.builder()
                .loginId(user.getLoginId())
                .name(user.getName())
                .birthday(user.getBirthday())
                .phone(user.getPhone())
                .signupType(user.getSignupType())
                .userType(user.getUserType())
                .email(user.getEmail())
                .emailVerified(user.getEmailVerified())
                .build();
    }
}
