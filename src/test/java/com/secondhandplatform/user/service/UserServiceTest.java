package com.secondhandplatform.user.service;

import com.secondhandplatform.common.exception.DuplicateException;
import com.secondhandplatform.user.domain.User;
import com.secondhandplatform.user.domain.UserRepository;
import com.secondhandplatform.user.dto.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("존재하지 않는 아이디로 회원가입을 시도한다.")
    void checkLoginIdAvailability() {
        // given
        String username = "memberA";

        //when
        Response response = userService.checkLoginIdAvailability(username);

        //then
        assertThat(response.getMessage()).isEqualTo(Response.USERNAME_OK);
    }

    @Test
    @DisplayName("존재하는 아이디로 회원가입을 시도한다.")
    void checkLoginIdAvailability2() {
        // given
        String username = "memberA";

        User user = User.builder()
                .username(username)
                .build();

        userRepository.save(user);

        //when //then
        assertThatThrownBy(() -> userService.checkLoginIdAvailability(username))
                .isInstanceOf(DuplicateException.class);
    }
}