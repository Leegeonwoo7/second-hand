package com.secondhandplatform.payment.controller;

import com.secondhandplatform.payment.dto.request.OrderForm;
import com.secondhandplatform.payment.dto.response.KakaoPayApproveResponse;
import com.secondhandplatform.payment.dto.response.KakaoPayReadyResponse;
import com.secondhandplatform.payment.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/payment")
public class PaymentController {

    private final KakaoPayService kakaoPayService;

    private String tid = "";

    @PostMapping
    public KakaoPayReadyResponse payReady(@RequestBody OrderForm request) {
        String itemName = request.getItemName();
        int totalAmount = request.getTotalAmount();

        KakaoPayReadyResponse response = kakaoPayService.payReady(itemName, totalAmount);
        tid = response.getTid();
        log.info("payReady() - response: {}", response);
        return response;
    }

    @GetMapping("/completed")
    public String payCompleted(@RequestParam("pg_token") String pgToken) {
        KakaoPayApproveResponse response = kakaoPayService.payApprove(tid, pgToken);
        return "redirect:/payment/success";
    }
}
