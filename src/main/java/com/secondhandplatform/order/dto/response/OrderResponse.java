package com.secondhandplatform.order.dto.request;

import com.secondhandplatform.delivery.domain.Delivery;
import com.secondhandplatform.order.domain.OrderStatus;
import com.secondhandplatform.payment.domain.Payment;
import com.secondhandplatform.product.domain.Product;
import com.secondhandplatform.user.domain.User;
import lombok.Getter;

@Getter
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
}
