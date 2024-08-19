package com.secondhandplatform.repository;

import com.secondhandplatform.domain.user.SignupType;
import com.secondhandplatform.domain.user.User;
import com.secondhandplatform.domain.user.UserType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
    }

   @Test
   @Order(1)
   @DisplayName("이메일 인증시 이미 존재하는 이메일인지 확인한다 - 사용불가능한 이메일")
   void existsByEmailSuccess() {
       // given
       LocalDate birthday = LocalDate.of(1998, 6, 2);
       User userA = createUser("userA", "1234", "userA@email.com", "01012341234", birthday, UserType.USER, SignupType.APP, false);
       userRepository.save(userA);

       String existEmail = "userA@email.com";

       //when
       boolean result = userRepository.existsByEmail(existEmail);

       //then
       assertThat(result).isTrue();
   }

    @Test
    @Order(2)
    @DisplayName("이메일 인증시 이미 존재하는 이메일인지 확인한다 - 사용가능한 이메일")
    void existsByEmailFail() {
        // given
        LocalDate birthday = LocalDate.of(1998, 6, 2);
        User userA = createUser("userA", "1234", "userA@email.com", "01012341234", birthday, UserType.USER, SignupType.APP, false);
        userRepository.save(userA);

        String existEmail = "new@email.com";

        //when
        boolean result = userRepository.existsByEmail(existEmail);

        //then
        assertThat(result).isFalse();
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