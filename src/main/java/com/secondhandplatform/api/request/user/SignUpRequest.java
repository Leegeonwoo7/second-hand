package com.secondhandplatform.api.request.user;

import com.secondhandplatform.domain.user.SignupType;
import com.secondhandplatform.domain.user.User;
import com.secondhandplatform.domain.user.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignUpRequest {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    private LocalDate birthday;

    @NotBlank
    private String name;

    private boolean emailVerified;
    private UserType userType;
    private SignupType signupType;

    @Builder
    private SignUpRequest(String loginId, String password, String email, String phone, LocalDate birthday, String name, UserType userType, SignupType signupType) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.name = name;
        this.userType = userType;
        this.signupType = signupType;
        //TODO emailVerified 임의설정
        this.emailVerified = true;
    }

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
                .emailVerified(this.emailVerified)
                .build();
    }

}
