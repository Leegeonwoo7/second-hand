package com.secondhandplatform.delivery.controller;

import com.secondhandplatform.delivery.service.DeliverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {

    private final DeliverService deliverService;

    @GetMapping
    public ResponseEntity<?> getUserAddress() {
        Authentication authentication = SecurityContextHolder.getContext()
                                    .getAuthentication();
        Long userId = Long.parseLong(authentication.getName());

        String userAddress = deliverService.findUserAddress(userId);

        if (!userAddress.isEmpty()) {
            return ResponseEntity.ok("배송지 직접입력하는 응답");
        }
        return ResponseEntity.ok(userAddress);
    }
}
