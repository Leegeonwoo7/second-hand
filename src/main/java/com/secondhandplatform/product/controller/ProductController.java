package com.secondhandplatform.product.controller;

import com.secondhandplatform.product.dto.request.EditProductRequest;
import com.secondhandplatform.product.dto.request.RegisterProductRequest;
import com.secondhandplatform.product.dto.response.ProductResponse;
import com.secondhandplatform.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/new")
    public ResponseEntity<?> registerProduct(@RequestBody RegisterProductRequest request) {
        log.debug("userId: {}", request.getUserId());
        ProductResponse response = productService.registerProduct(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editProduct(@RequestBody EditProductRequest request) {
        ProductResponse response = productService.editProduct(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/{productId}")
    public ResponseEntity<?> findProduct(@PathVariable Long userId, @PathVariable Long productId) {
        ProductResponse response = productService.findProduct(userId, productId);

        return ResponseEntity.ok(response);
    }
}
