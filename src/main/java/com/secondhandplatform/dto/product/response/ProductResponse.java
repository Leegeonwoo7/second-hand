package com.secondhandplatform.dto.product.response;

import com.secondhandplatform.domain.product.Category;
import com.secondhandplatform.domain.product.Product;
import com.secondhandplatform.domain.product.ProductStatus;
import com.secondhandplatform.domain.product.SellingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private int price;
    private int quantity;
    private Category category;
    private ProductStatus productStatus;
    private SellingStatus sellingStatus;

    private Long userId;

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .category(product.getCategory())
                .productStatus(product.getProductStatus())
                .sellingStatus(product.getSellingStatus())
                .userId(product.getUser().getId())
                .build();
    }
}
