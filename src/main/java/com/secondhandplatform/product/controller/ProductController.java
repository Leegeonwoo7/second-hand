package com.secondhandplatform.product.controller;

import com.secondhandplatform.product.dto.request.EditProductRequest;
import com.secondhandplatform.product.dto.request.RegisterProductRequest;
import com.secondhandplatform.product.dto.request.RemoveProductRequest;
import com.secondhandplatform.product.dto.response.ProductResponse;
import com.secondhandplatform.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/new")
    public ResponseEntity<?> registerProduct(@RequestBody RegisterProductRequest request) {
        // 시큐리티에서 헤더에 있는 토큰으로부터 식별자인 userId를 읽어옴
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        Long userId = Long.parseLong(authentication.getName());

        log.info("POST /products/new - userId: {}", userId);

        ProductResponse response = productService.registerProduct(request, userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editProduct(@RequestBody EditProductRequest request) {
        ProductResponse response = productService.editProduct(request);

        return ResponseEntity.ok(response);
    }

    //상품 단건조회
    @GetMapping("/{productId}")
    public ResponseEntity<?> findProduct(@PathVariable Long productId) {
        log.info("GET - /products/{productId}");
        ProductResponse response = productService.findProduct(productId);

        return ResponseEntity.ok(response);
    }

    //회원이 등록한 상품목록 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> findProductList(@PathVariable Long userId) {
        List<ProductResponse> response = productService.findProductsByUser(userId);

        return ResponseEntity.ok(response);
    }

    //메인화면 상품목록조회
    @GetMapping
    public ResponseEntity<?> findAllProduct() {
        List<ProductResponse> response = productService.findProducts();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public void deleteProduct(@RequestBody RemoveProductRequest request) {
        productService.removeProduct(request);
    }
}
