package com.secondhandplatform.product.service;

import com.secondhandplatform.product.domain.Product;
import com.secondhandplatform.product.domain.ProductRepository;
import com.secondhandplatform.product.dto.request.EditProductRequest;
import com.secondhandplatform.product.dto.request.RegisterProductRequest;
import com.secondhandplatform.product.dto.response.ProductResponse;
import com.secondhandplatform.user.domain.User;
import com.secondhandplatform.user.domain.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("회원이 등록한 상품목록들을 조회한다.")
    void findProductsByUser() {
        //given


        //when

        //then
    }

    @Test
    @DisplayName("등록되어있는 상품정보를 수정한다.")
    void editProduct() {
        Product product = Product.builder()
                .name("청바지")
                .price(10000)
                .description("상태좋은 청바지 팝니다.")
                .build();

        Product savedProduct = productRepository.save(product);

        EditProductRequest request = EditProductRequest.builder()
                .productId(savedProduct.getId())
                .name("리바이스 청바지")
                .price(7000)
                .build();

        //when
        ProductResponse response = productService.editProduct(request);

        //then
        assertThat(response.getId()).isEqualTo(savedProduct.getId());
        assertThat(response.getName()).isEqualTo("리바이스 청바지");
        assertThat(response.getPrice()).isEqualTo(7000);
    }
}