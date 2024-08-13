package com.secondhandplatform.api.request.user;

import com.secondhandplatform.domain.user.SignupType;
import com.secondhandplatform.domain.user.User;
import com.secondhandplatform.domain.user.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignupRequest {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    private LocalDate birthday;

    @NotBlank
    private String name;

    private UserType userType;
    private SignupType signupType;

    public User toEntity() {
        return User.builder()
                .loginId(this.loginId)
                .password(this.password)
                .name(this.loginId)
                .email(this.email)
                .signupType(this.signupType)
                .birthday(this.birthday)
                .phone(this.phone)
                .userType(this.userType)
                .build();
    }
}
