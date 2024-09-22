package com.secondhandplatform.order.service;

import com.secondhandplatform.common.exception.BadRequestException;
import com.secondhandplatform.order.domain.Order;
import com.secondhandplatform.order.domain.OrderRepository;
import com.secondhandplatform.order.dto.request.OrderRequest;
import com.secondhandplatform.order.dto.response.OrderResponse;
import com.secondhandplatform.product.domain.Product;
import com.secondhandplatform.product.domain.ProductRepository;
import com.secondhandplatform.user.domain.User;
import com.secondhandplatform.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.secondhandplatform.common.exception.BadRequestException.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    /**
     * 구매자가 상품을 구매하면 구매자와 판매자는 해당 주문을 알고있어야함
     *
     */

    public OrderResponse createOrder(OrderRequest orderRequest) {
        int quantity = orderRequest.getQuantity();

        Long buyerId = orderRequest.getBuyerId();
        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new BadRequestException(NOT_EXIST_USER));

        Long productId = orderRequest.getProductId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BadRequestException(NOT_EXIST_PRODUCT));
        User seller = product.getUser();

        if (seller == null) {
            log.error("판매자 정의되지않음");
        }

        Order order = Order.createOrder(buyer, seller, product, quantity, null, null);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }
}
