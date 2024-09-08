package com.secondhandplatform.product.domain;

import lombok.Getter;

@Getter
public enum SellingStatus {

    SELLING("판매중"),
    RESERVED("예약중"),
    COMPLETED("거래완료");

    private final String description;

    SellingStatus(String description) {
        this.description = description;
    }
}
