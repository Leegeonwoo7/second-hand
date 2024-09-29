package com.secondhandplatform.order.controller;

import com.secondhandplatform.order.dto.request.OrderRequest;
import com.secondhandplatform.order.dto.response.OrderResponse;
import com.secondhandplatform.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/new")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        Long userId = Long.parseLong(authentication.getName());

        OrderResponse response = orderService.createOrder(orderRequest, userId);
        return ResponseEntity.ok(response);
    }

    // 주문정보 확인 - OrderView.jsx

    /**
     * 주문정보 확인에서는 orderId가 필요없을지도
     * 주문을 호출하는게 아니라, 주문을 하기위한 요소들을 호출해야함
     * 1. 회원의 주소정보
     * 2. 결제 금액
     * else. 회원의 적립포인트
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<?> findOrder(@PathVariable Long orderId) {
        OrderResponse response = orderService.findOrderDetail(orderId);

        return ResponseEntity.ok(response);
    }
}
