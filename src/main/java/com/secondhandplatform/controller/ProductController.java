package com.secondhandplatform.controller;

import com.secondhandplatform.dto.product.request.ProductSaveRequest;
import com.secondhandplatform.dto.product.response.ProductResponse;
import com.secondhandplatform.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/new")
    public ResponseEntity<?> registerProduct(@RequestBody ProductSaveRequest request, @AuthenticationPrincipal String loginId) {
        log.debug("ProductController - token userId: {}", loginId);

        ProductResponse response = productService.register(request, loginId);
        return ResponseEntity.ok(response);
    }
}
