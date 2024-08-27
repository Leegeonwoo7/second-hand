package com.secondhandplatform.repository;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;



    @Test
    @Order(1)
    @DisplayName("존재하지 않는 아이디")
    void asd() {
        // given
        String nonExistId = "nonExistId";

        //when
        boolean result = userRepository.existsByLoginId(nonExistId);

        //then
        assertThat(result).isFalse();
    }
}