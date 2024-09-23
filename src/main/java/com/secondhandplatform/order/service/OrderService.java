package com.secondhandplatform.order.service;

import com.secondhandplatform.common.exception.BadRequestException;
import com.secondhandplatform.delivery.domain.Address;
import com.secondhandplatform.delivery.domain.Delivery;
import com.secondhandplatform.delivery.domain.DeliveryRepository;
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
import org.springframework.transaction.annotation.Transactional;

import static com.secondhandplatform.common.exception.BadRequestException.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final DeliveryRepository deliveryRepository;

    /**
     * 사용자가 주문을 등록하는 그래프
     * 1. 구매하려는 상품의 구매버튼 클릭
     * 2. 수량선택
     * 3. 배송지입력
     * 4. 결제수단선택
     * 5. 결제 (OrderStatus: INIT, Delivery: READY)
     */
    // 주문등록
    public OrderResponse createOrder(OrderRequest orderRequest) {
        int quantity = orderRequest.getQuantity();
        Long buyerId = orderRequest.getBuyerId();
        Long productId = orderRequest.getProductId();

        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new BadRequestException(NOT_EXIST_USER));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BadRequestException(NOT_EXIST_PRODUCT));
        User seller = product.getUser();
        Delivery delivery = initDelivery(orderRequest, buyer);
        deliveryRepository.save(delivery);

        //결제로직

        Order order = Order.createOrder(buyer, seller, product, quantity, null, delivery);
        Order savedOrder = orderRepository.save(order);

        product.changeSellingStatus();

        return OrderResponse.of(savedOrder);
    }

    private Delivery initDelivery(OrderRequest orderRequest, User buyer) {
        Address address = orderRequest.getAddress();
        if (address == null) {
            address = buyer.getAddress();
        }
        return Delivery.builder()
                .address(address)
                .user(buyer)
                .build();
    }

    // 주문취소

}
