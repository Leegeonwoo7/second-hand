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
    @DisplayName("회원을 저장한다.")
    void save() {
        // given
        LocalDate birthdate = LocalDate.of(1998, 5, 1);
        User userA = createUser("userA",
                "1234",
                "userA@example.com",
                "01012341234",
                birthdate,
                UserType.USER,
                SignupType.APP,
                false);

        //when
        User saveUser = userRepository.save(userA);

        //then
        assertThat(saveUser.getId()).isEqualTo(1L);
    }

    @Test
    @Order(2)
    @DisplayName("이미 저장된 로그인 아이디인지 확인한다.")
    void existsByLoginId() {
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

        //when
        boolean result = userRepository.existsByLoginId(loginId);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @Order(3)
    @DisplayName("로그인 요청시 존재하는 회원인지 확인한다.")
    void findByLoginIdSuccess() {
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

        //when
        User user = userRepository.findByLoginId(loginId);

        //then
        assertNotNull(user);
        assertThat(user.getLoginId()).isEqualTo(loginId);
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