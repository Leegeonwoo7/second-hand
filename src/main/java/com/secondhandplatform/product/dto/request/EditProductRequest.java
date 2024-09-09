package com.secondhandplatform.product.dto.request;

import com.secondhandplatform.product.domain.Category;
import com.secondhandplatform.product.domain.ProductStatus;
import com.secondhandplatform.product.domain.SellingStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EditProductRequest {

    private Long productId;
    private String name;
    private String description;
    private int price;
    private int quantity;
    private Category category;
    private ProductStatus productStatus;
    private SellingStatus sellingStatus;


}
