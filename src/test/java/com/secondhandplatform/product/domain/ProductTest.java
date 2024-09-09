package com.secondhandplatform.product.domain;

import com.secondhandplatform.product.dto.request.EditProductRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class ProductTest {

    @Test
    @DisplayName("상품정보를 수정한다.")
    void updateProduct() {
        //given
        Product product = Product.builder()
                .name("청바지")
                .description("싸게 판매합니다.")
                .price(20000)
                .productStatus(ProductStatus.GOOD)
                .category(Category.PANTS)
                .quantity(1)
                .sellingStatus(SellingStatus.SELLING)
                .build();

        EditProductRequest request = EditProductRequest.builder()
                .name("리바이스 청바지")
                .description("가격을 조금 내립니다.")
                .price(15000)
                .build();

        //when
        Product updatedProduct = product.updateProduct(request);

        //then
        assertThat(updatedProduct.getName()).isEqualTo("리바이스 청바지");
        assertThat(updatedProduct.getDescription()).isEqualTo("가격을 조금 내립니다.");
        assertThat(updatedProduct.getPrice()).isEqualTo(15000);
    }
}