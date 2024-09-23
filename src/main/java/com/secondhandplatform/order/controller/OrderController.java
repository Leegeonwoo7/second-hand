package com.secondhandplatform.order.controller;

import com.secondhandplatform.order.dto.request.OrderRequest;
import com.secondhandplatform.order.dto.response.OrderResponse;
import com.secondhandplatform.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/new")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        log.debug("/orders/new 실행");
        OrderResponse response = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(response);
    }
}
