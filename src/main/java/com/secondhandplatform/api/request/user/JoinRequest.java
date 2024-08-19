package com.secondhandplatform.api.request.user;

import com.secondhandplatform.domain.user.SignupType;
import com.secondhandplatform.domain.user.User;
import com.secondhandplatform.domain.user.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class JoinRequest {

    @NotBlank
    private String loginId;

    @NotBlank
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{10,}$", message = "비밀번호는 반드시 특수문자, 영문자, 숫자 조합으로 10자리 이상이어야 합니다.")
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    private LocalDate birthday;

    @NotBlank
    private String certificationNumber;

    private String name;
    private UserType userType;
    private SignupType signupType;

    @Builder
    private JoinRequest(String loginId, String password, String email, String phone, LocalDate birthday, String certificationNumber, String name, UserType userType, SignupType signupType) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.certificationNumber = certificationNumber;
        this.name = name;
        this.userType = userType;
        this.signupType = signupType;
    }

    // TODO 유저타입, 가입경로 임시설정
    public User toEntity() {
        String nameToUse = this.name == null ? loginId : name;

        return User.builder()
                .loginId(this.loginId)
                .password(this.password)
                .email(this.email)
                .phone(this.phone)
                .birthday(this.birthday)
                .name(nameToUse)
                .userType(UserType.USER)
                .signupType(SignupType.APP)
                .build();
    }
}
