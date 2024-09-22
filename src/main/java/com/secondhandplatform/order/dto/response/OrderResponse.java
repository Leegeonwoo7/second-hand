package com.secondhandplatform.order.dto.response;

import com.secondhandplatform.delivery.domain.Delivery;
import com.secondhandplatform.order.domain.Order;
import com.secondhandplatform.order.domain.OrderStatus;
import com.secondhandplatform.payment.domain.Payment;
import com.secondhandplatform.product.domain.Product;
import com.secondhandplatform.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponse {

    private Long id;
    private OrderStatus orderStatus;
    private int totalPrice;
    private int quantity;
    private Payment payment;
    private Delivery delivery;
    private User buyer;
    private User seller;
    private Product product;

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .quantity(order.getQuantity())
                .payment(order.getPayment())
                .delivery(order.getDelivery())
                .buyer(order.getBuyer())
                .seller(order.getSeller())
                .product(order.getProduct())
                .build();¥
    }
}
