package com.secondhandplatform.product.service;

import com.secondhandplatform.product.domain.ProductRepository;
import com.secondhandplatform.product.dto.request.RegisterProductRequest;
import com.secondhandplatform.product.dto.response.ProductResponse;
import com.secondhandplatform.user.domain.User;
import com.secondhandplatform.user.domain.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("상품을 등록한다.")
    void registerProduct() {
        //given
        User user = User.builder()
                .username("userA")
                .password("1234")
                .build();
        User saveUser = userRepository.save(user);

        RegisterProductRequest product = RegisterProductRequest.builder()
                .name("아이패드")
                .price(100000)
                .userId(saveUser.getId())
                .build();

        //when
        ProductResponse response = productService.registerProduct(product);

        //then
        assertThat(response.getName()).isEqualTo("아이패드");
        assertThat(response.getUser()).isEqualTo(user);
//        assertThat(response).extracting("name", "price")
//                .containsExactly(tuple("아이패드"), tuple(100000));
    }
}