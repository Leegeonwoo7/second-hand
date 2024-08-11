package com.secondhandplatform.service.user;

import com.secondhandplatform.api.request.user.IdCheckRequest;
import com.secondhandplatform.domain.user.SignupType;
import com.secondhandplatform.domain.user.User;
import com.secondhandplatform.domain.user.UserType;
import com.secondhandplatform.repository.UserRepository;
import com.secondhandplatform.service.user.response.IdCheckResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    @DisplayName("아이디가 중복이면 Duplicate 응답을 반환한다.")
    void idCheck() {
        // given
        String loginId = "userA";
        User userA = createUser(loginId,
                "1234",
                "userA@example.com",
                "01012341234",
                LocalDate.now(),
                UserType.USER,
                SignupType.APP,
                false);
        userRepository.save(userA);

        IdCheckRequest request = IdCheckRequest.builder()
                .loginId(loginId)
                .build();

        //when
        IdCheckResponse response = userService.checkLoginId(request);

        //then
        assertThat(response.getResult()).isEqualTo("DUPLICATE_ID");
        assertThat(response.getLoginId()).isEqualTo(loginId);
    }


    private static User createUser(
            String loginId,
            String password,
            String email,
            String phone,
            LocalDate birthday,
            UserType userType,
            SignupType signupType,
            boolean emailVerified
    ) {
        return User.builder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .phone(phone)
                .birthday(birthday)
                .userType(userType)
                .signupType(signupType)
                .emailVerified(emailVerified)
                .build();
    }
}