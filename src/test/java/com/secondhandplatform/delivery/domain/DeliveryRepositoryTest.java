package com.secondhandplatform.delivery.domain;

import com.secondhandplatform.user.domain.User;
import com.secondhandplatform.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class DeliveryRepositoryTest {

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("유저의 모든 배달지 정보데이터를 조회한다.")
    void findAllById() {
        //given
        Address address1 = new Address("경기도 과천시", "과천길 165", "102동 1303호");
        Address address2 = new Address("경기도 성남시", "이배재로 15", "청산빌라 103호");
        Address address3 = new Address("서울시 역삼동", "역삼로 103", "1101동 406호");


        User userA = User.builder()
                .username("userA")
                .password("1234")
                .address(address1)
                .build();
        userA.registerAddress(address2);

        User userB = User.builder()
                .username("userB")
                .password("1234")
                .address(address3)
                .build();

//        userRepository.save(userA)

        //when
        
        //then
    }
}