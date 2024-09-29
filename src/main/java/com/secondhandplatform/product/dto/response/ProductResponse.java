package com.secondhandplatform.product.dto.response;

import com.secondhandplatform.product.domain.Category;
import com.secondhandplatform.product.domain.Product;
import com.secondhandplatform.product.domain.ProductStatus;
import com.secondhandplatform.product.domain.SellingStatus;
import com.secondhandplatform.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {

    private Long id;
    private User user;
    private String name;
    private String description;
    private int price;
    private Category category;
    private ProductStatus productStatus;
    private SellingStatus sellingStatus;

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .user(product.getUser())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .productStatus(product.getProductStatus())
                .sellingStatus(product.getSellingStatus())
                .build();
    }
}
