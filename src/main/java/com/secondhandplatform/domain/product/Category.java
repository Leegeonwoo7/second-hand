package com.secondhandplatform.domain.product;

public enum Category {
    ACCSESSORIES("악세서리"),
    TOPS("상의"),
    SHOES("신발"),
    HATS("모자"),
    PANTS("바지");

    private final String description;

    Category(String description) {
        this.description = description;
    }
}
