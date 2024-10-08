package com.secondhandplatform.product.domain;

import com.secondhandplatform.user.domain.User;
import com.secondhandplatform.user.domain.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("여러 상품을 조회한다.")
    void finaAllProduct() {
        // given
        User user = User.builder()
                .username("userA")
                .build();
        User savedUser = userRepository.save(user);

        Product product1 = Product.builder()
                .name("중고 바지")
                .price(10000)
                .build();
        product1.registerBy(user);

        Product product2 = Product.builder()
                .name("중고 상의")
                .price(15000)
                .build();
        product2.registerBy(user);

        productRepository.saveAll(List.of(product1, product2));

        //when
        List<Product> productList = productRepository.findByUser(user);

        //then
        assertThat(productList).hasSize(2);
    }
    
    @Test
    @DisplayName("User객체로 등록한상품목록들을 조회한다.")
    void findByUser() {
        //given
        User seller = User.builder()
                .username("seller")
                .build();
        userRepository.save(seller);

        Product product1 = Product.builder()
                .name("아이패드")
                .price(100000)
                .build();

        Product product2= Product.builder()
                .name("아이폰")
                .price(200000)
                .build();

        product1.registerBy(seller);
        product2.registerBy(seller);

        productRepository.saveAll(List.of(product1, product2));

        //when
        List<Product> productList = productRepository.findByUser(seller);

        //then
        assertThat(productList).hasSize(2);
        assertThat(productList).extracting("name", "price")
                .containsExactlyInAnyOrder(tuple("아이폰", 200000), tuple("아이패드", 100000));
    }
}