package com.secondhandplatform.product.controller;

import com.secondhandplatform.dto.product.request.ProductSaveRequest;
import com.secondhandplatform.dto.product.response.ProductResponse;
import com.secondhandplatform.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/new")
    public ResponseEntity<?> registerProduct(@RequestBody ProductSaveRequest request, @AuthenticationPrincipal String loginId) {
        ProductResponse response = productService.register(request, loginId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Long productId) {
        ProductResponse response = productService.findProduct(productId);

        if (response == null) {
            return ResponseEntity.badRequest()
                    .body(null);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getProducts(@PathVariable Long userId) {
        List<ProductResponse> products = productService.findProducts(userId);
        return ResponseEntity.ok(products);
    }
}
