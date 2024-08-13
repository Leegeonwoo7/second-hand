package com.secondhandplatform.service.user.response;

import com.secondhandplatform.domain.user.SignupType;
import com.secondhandplatform.domain.user.UserType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignupResponse {

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

    @Builder
    private SignupResponse(String loginId, String password, String email, String phone, LocalDate birthday, String name, UserType userType, SignupType signupType, String message) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.name = name;
        this.userType = userType;
        this.signupType = signupType;
        this.message = message;
    }
}
