package com.secondhandplatform.order.service;

import com.secondhandplatform.common.exception.BadRequestException;
import com.secondhandplatform.delivery.domain.Address;
import com.secondhandplatform.delivery.domain.DeliveryRepository;
import com.secondhandplatform.order.domain.Order;
import com.secondhandplatform.order.domain.OrderRepository;
import com.secondhandplatform.order.domain.OrderStatus;
import com.secondhandplatform.order.dto.request.OrderRequest;
import com.secondhandplatform.order.dto.response.OrderResponse;
import com.secondhandplatform.product.domain.Product;
import com.secondhandplatform.product.domain.ProductRepository;
import com.secondhandplatform.product.domain.SellingStatus;
import com.secondhandplatform.user.domain.User;
import com.secondhandplatform.user.domain.UserRepository;
import org.junit.jupiter.api.AfterEach;
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
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DeliveryRepository deliveryRepository;

    @AfterEach
    void tearDown() {
        orderRepository.deleteAllInBatch();
        deliveryRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("주문을 생성한다.")
    void createOrder() {
        //given
        User buyer = createUser("buyer");
        User seller = createUser("seller");

        Product product = registerProduct(seller, "상품A", 10000);

        OrderRequest request = OrderRequest.builder()
                .buyerId(buyer.getId())
                .productId(product.getId())
                .quantity(1)
                .build();

        //when
        OrderResponse response = orderService.createOrder(request);

        //then
        assertThat(response.getBuyer()).isEqualTo(buyer);
        assertThat(response.getSeller()).isEqualTo(seller);
        assertThat(response.getProduct()).isEqualTo(product);
    }

    @Test
    @DisplayName("등록된 주문을 조회한다.")
    void findOrder() {
        //given
        User buyer = createUser("buyer");
        User seller = createUser("seller");

        Product product = registerProduct(seller, "상품A", 10000);

        OrderRequest request = OrderRequest.builder()
                .buyerId(buyer.getId())
                .productId(product.getId())
                .quantity(2)
                .build();
        OrderResponse response = orderService.createOrder(request);

        //when
        Order order = orderRepository.findById(response.getId())
                .orElseThrow(() -> new RuntimeException());

        //then
        assertThat(order.getTotalPrice()).isEqualTo(20000);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.INIT);
        assertThat(order.getBuyer()).isEqualTo(buyer);
        assertThat(order.getSeller()).isEqualTo(seller);
        assertThat(order.getProduct()).isEqualTo(product);
    }

    @Test
    @DisplayName("상품주문시 구매자를 조회하지 못하면 예외가 발생한다.")
    void createOrderFail() {
        //given
        Long userId = 111L;
        User seller = createUser("seller");

        Product product = registerProduct(seller, "상품A", 10000);

        OrderRequest request = OrderRequest.builder()
                .buyerId(userId)
                .productId(product.getId())
                .quantity(1)
                .build();

        //when //then
        assertThatThrownBy(() -> orderService.createOrder(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(BadRequestException.NOT_EXIST_USER);
    }
    
    @Test
    @DisplayName("주문이 완료되면 상품은 \"예약중\"으로 변경된다.")
    void createOrderState() {
        //given
        User buyer = createUser("buyer");
        User seller = createUser("seller");

        Product product = registerProduct(seller, "상품A", 10000);

        OrderRequest request = OrderRequest.builder()
                .buyerId(buyer.getId())
                .productId(product.getId())
                .quantity(1)
                .build();

        //when
        OrderResponse response = orderService.createOrder(request);

        //then
        Product savedOrder = response.getProduct();
        assertThat(savedOrder.getSellingStatus()).isEqualTo(SellingStatus.RESERVED);
    }
    
    @Test
    @DisplayName("주문생성시 배달지 정보가 직접입력되지 않을경우 회원의 기존 주소로 배송지를 등록한다.")
    void createOrderDelivery() {
        //given
        Address address = new Address("서울시", "강남구 역삼로 131-2", "102동 4층");
        User buyer = createUserWithAddress("buyer", address);
        User seller = createUser("seller");


        Product product = registerProduct(seller, "상품A", 10000);

        OrderRequest request = OrderRequest.builder()
                .buyerId(buyer.getId())
                .productId(product.getId())
                .quantity(1)
                .build();

        //when
        OrderResponse response = orderService.createOrder(request);

        //then
        assertThat(response.getDelivery()
                .getAddress()).isEqualTo(address);
    }

    @Test
    @DisplayName("주문생성시 배달지 정보가 입력되면 입력된 정보를 배달지로 지정한다.")
    void createOrderDelivery2() {
        //given
        User buyer = createUser("buyer");
        User seller = createUser("seller");


        Product product = registerProduct(seller, "상품A", 10000);

        Address address = new Address("서울시 강남구", "역삼로 13", "102-123");
        OrderRequest request = OrderRequest.builder()
                .buyerId(buyer.getId())
                .productId(product.getId())
                .quantity(1)
                .address(address)
                .build();

        //when
        OrderResponse response = orderService.createOrder(request);

        //then
        assertThat(response.getDelivery()
                .getAddress()).isEqualTo(address);
    }

    private Product registerProduct(User seller, String name, int price) {
        Product product = Product.builder()
                .name(name)
                .price(price)
                .build();
        product.registerBy(seller);
        return productRepository.save(product);
    }

    private User createUser(String username) {
        User user = User.builder()
                .username(username)
                .build();

        return userRepository.save(user);
    }

    private User createUserWithAddress(String username, Address address) {
        User user = User.builder()
                .username(username)
                .address(address)
                .build();

        return userRepository.save(user);
    }
}