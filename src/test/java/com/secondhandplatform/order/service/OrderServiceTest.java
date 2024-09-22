package com.secondhandplatform.order.service;

import com.secondhandplatform.order.dto.request.OrderRequest;
import com.secondhandplatform.order.dto.response.OrderResponse;
import com.secondhandplatform.product.domain.Product;
import com.secondhandplatform.product.domain.ProductRepository;
import com.secondhandplatform.user.domain.User;
import com.secondhandplatform.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("주문을 생성한다.")
    void createOrder() {
        //given
        User buyer = User.builder()
                .username("buyer")
                .build();
        User seller = User.builder()
                .username("seller")
                .build();

        User savedBuyer = userRepository.save(buyer);
        userRepository.save(seller);

        Product product = Product.builder()
                .name("상품A")
                .price(10000)
                .build();
        product.registerBy(seller);

        Product savedProduct = productRepository.save(product);

        OrderRequest request = OrderRequest.builder()
                .buyerId(savedBuyer.getId())
                .productId(savedProduct.getId())
                .quantity(1)
                .build();

        //when
        OrderResponse response = orderService.createOrder(request);

        //then
        assertThat(response.getBuyer()).isEqualTo(buyer);
        assertThat(response.getSeller()).isEqualTo(seller);
        assertThat(response.getProduct()).isEqualTo(product);
    }
}