package com.secondhandplatform.product.domain;

import lombok.Getter;

@Getter
public enum ProductStatus {

    NEW("새상품"),
    LIKE_NEW("거의 새상품"),
    GOOD("사용감 있음"),
    FAIR("사용감 많음");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }
}
