package com.secondhandplatform.dto.user.request;

import com.secondhandplatform.domain.user.SignupType;
import com.secondhandplatform.domain.user.User;
import com.secondhandplatform.domain.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Slf4j
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDto {

    private String loginId;
    private String password;
    private String email;
    private String phone;
    private LocalDate birthday;
    private String name;
    private UserType userType;
    private SignupType signupType;

    public User toEntity(CreateUserRequestDto request, String encodedPassword) {
        String nameToUse = "";

        if (!StringUtils.hasText(request.getName())) {
            nameToUse = request.getLoginId();
        } else {
            nameToUse = request.getName();
        }
        log.debug("create user by nameToUse: {}", nameToUse);
        return User.builder()
                .loginId(this.loginId)
                .password(encodedPassword)
                .email(this.email)
                .phone(this.phone)
                .birthday(this.birthday)
                .name(nameToUse)
                .userType(UserType.USER)
                .signupType(SignupType.APP)
                .build();
    }
}
