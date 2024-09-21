package com.secondhandplatform.user.dto.request;

import com.secondhandplatform.user.domain.SignupType;
import com.secondhandplatform.user.domain.User;
import com.secondhandplatform.user.domain.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class JoinRequest {

    private String username;
    private String password;
    private String email;
    private String phone;
    private LocalDate birthday;
    private String name;
    private UserType userType;
    private SignupType signupType;

    public void setPassword(String password) {
        this.password = password;
    }

    public User toEntity() {
        String toUseName = !StringUtils.hasText(this.name) ? username : this.name;
        System.out.println("toUseName = " + toUseName);
        
        return User.builder()
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .phone(this.phone)
                .birthday(this.birthday)
                .name(toUseName)
                .userType(UserType.USER)
                .signupType(SignupType.APP)
                .build();
    }
}
