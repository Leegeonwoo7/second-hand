package com.secondhandplatform.order.domain;

import com.secondhandplatform.delivery.domain.Delivery;
import com.secondhandplatform.payment.domain.Payment;
import com.secondhandplatform.product.domain.Product;
import com.secondhandplatform.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;


class OrderTest {
    
    @Test
    @DisplayName("주문을 생성한다.")
    void createOrder() {
        //given
        Product product1 = Product.builder()
                .name("청바지")
                .price(10000)
                .build();

        User buyer = User.builder()
                .username("buyer")
                .password("1234")
                .build();

        User seller = User.builder()
                .username("seller")
                .password("1234")
                .build();

        Payment payment = new Payment();
        Delivery delivery = new Delivery();

        //when
        Order order = Order.createOrder(buyer, seller, product1, 2, payment, delivery);

        //then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.INIT);
        assertThat(order.getBuyer()).isEqualTo(buyer);
        assertThat(order.getSeller()).isEqualTo(seller);
        assertThat(order.getTotalPrice()).isEqualTo(20000);
    }
}