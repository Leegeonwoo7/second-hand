package com.secondhandplatform.product.domain;

import com.secondhandplatform.user.domain.User;
import com.secondhandplatform.user.domain.UserRepository;
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
}