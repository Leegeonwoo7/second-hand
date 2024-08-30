package com.secondhandplatform.dto.product.request;

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
public class ProductSaveRequest {

    private String name;
    private String description;
    private int price;
    private int quantity;
    private Category category;
    private ProductStatus productStatus;
    private SellingStatus sellingStatus;

    public Product toEntity(ProductSaveRequest request) {
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
