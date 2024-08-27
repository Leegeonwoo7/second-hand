package com.secondhandplatform.repository;

import com.secondhandplatform.domain.user.SignupType;
import com.secondhandplatform.domain.user.User;
import com.secondhandplatform.domain.user.UserType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
    }

    @Test
    @Order(1)
    @DisplayName("존재하지 않는 아이디")
    void nonExistId() {
        // given
        String nonExistId = "nonExistId";

        //when
        boolean result = userRepository.existsByLoginId(nonExistId);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @Order(2)
    @DisplayName("이미 존재하는 아이디")
    void existId() {
        // given
        String existId = "existId";
        User user = createUser(existId);
        userRepository.save(user);

        //when
        boolean result = userRepository.existsByLoginId(existId);

        //then
        assertThat(result).isTrue();
    }

    private User createUser(String existId) {
        LocalDate birthday = LocalDate.of(1992, 2, 15);

        return User.builder()
                .loginId(existId)
                .password("1234")
                .email("email")
                .phone("01012341234")
                .birthday(birthday)
                .userType(UserType.USER)
                .signupType(SignupType.APP)
                .build();
    }
}