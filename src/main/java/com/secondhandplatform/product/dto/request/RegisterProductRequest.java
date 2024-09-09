package com.secondhandplatform.product.dto.request;

import com.secondhandplatform.product.domain.Category;
import com.secondhandplatform.product.domain.Product;
import com.secondhandplatform.product.domain.ProductStatus;
import com.secondhandplatform.product.domain.SellingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RegisterProductRequest {

    private Long userId;
    private String name;
    private String description;
    private int price;
    private int quantity;
    private Category category;
    private ProductStatus productStatus;
    private SellingStatus sellingStatus;

    public Product toEntity() {
        return Product.builder()
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .quantity(this.quantity)
                .category(this.category)
                .productStatus(this.productStatus)
                .sellingStatus(this.sellingStatus)
                .build();
    }
}
